package com.pagacz.gsheet.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.pagacz.database.dto.BaseDto;
import com.pagacz.gsheet.mapper.SheetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleSheetService<T extends BaseDto> {

    private final Logger log = LoggerFactory.getLogger(GoogleSheetService.class);
    private final Sheets sheetsService;
    private final String sheetId;
    private final String gSheetRange;
    private final SheetMapper<T> sheetMapper;

    public GoogleSheetService(String credFilePath, String applicationName, String sheetId, String sheetRange, SheetMapper<T> sheetMapper) throws IOException, GeneralSecurityException {
        this.sheetId = sheetId;
        this.sheetMapper = sheetMapper;
        this.gSheetRange = sheetRange;
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        GoogleCredentials googleCredentials = ServiceAccountCredentials.fromStream(Objects.requireNonNull(GoogleSheetService.class.getResourceAsStream(credFilePath)))
                .createScoped(Collections.singletonList(SheetsScopes.SPREADSHEETS));
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(googleCredentials);
        sheetsService = new Sheets.Builder(httpTransport, jsonFactory, requestInitializer)
                .setApplicationName(applicationName)
                .build();
    }

    public List<List<Object>> getExistingData() {
        ValueRange existingSheetData = getExistingData(gSheetRange);
        if (existingSheetData != null) {
            return existingSheetData.getValues();
        } else {
            return Collections.emptyList();
        }
    }

    public void updateListInSheet(List<T> t) throws IOException {
        for (T data : t) {
            updateSingleInSheet(data.getIndex(), List.of(data.getColumnsForSheet()));
        }
    }

    public void addAllData(List<T> data) {
        for (T t : data) {
            try {
                prepareAndWriteToSheet(t);
            } catch (IOException | IllegalAccessException e) {
                log.error("error: ", e);
            }
        }
    }

    public Map<String, T> getExistingDataFromSheet() {
        List<List<Object>> existingData = getExistingData();
        Map<String, T> existingOffers = new HashMap<>();
        if (existingData != null) {
            existingData.forEach(o -> {
                T t1 = sheetMapper.map(o);
                t1.setIndex(existingData.indexOf(o));
                existingOffers.put(t1.getUniqueIdentifier(), t1);
            });
        }
        return existingOffers;
    }

    private void updateSingleInSheet(Integer indexRow, List<List<Object>> updateValues) throws IOException {
        ValueRange body = new ValueRange().setValues(updateValues);
        String updateRange = prepareUpdateRange(indexRow);
        sheetsService.spreadsheets()
                .values()
                .update(sheetId, updateRange, body)
                .setValueInputOption("RAW")
                .execute();
    }

    private String prepareUpdateRange(Integer indexRow) {
        indexRow += 2;
        Pattern pattern = Pattern.compile("(!.)(\\d)");
        Matcher matcher = pattern.matcher(gSheetRange);
        StringBuilder updateRange = new StringBuilder();
        while (matcher.find()) {
            String replacement = matcher.group(1) + indexRow;
            matcher.appendReplacement(updateRange, replacement);
        }
        matcher.appendTail(updateRange);
        return updateRange.toString() + indexRow;
    }

    private ValueRange getExistingData(String sheetRange) {
        ValueRange response = null;
        try {
            response = sheetsService.spreadsheets().values().get(sheetId, sheetRange).execute();
        } catch (IOException e) {
            log.error("Error occurred at getting data from spreadsheet stage", e);
        }
        return response;
    }

    private static List<List<Object>> generateValuesFromObject(Object object) throws IllegalAccessException {
        List<List<Object>> values = new ArrayList<>();
        List<Object> singleRow = new ArrayList<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(object);
            if (value instanceof LocalDateTime) {
                value = DateTime.parseRfc3339(value.toString()).toString();
            }
            singleRow.add(List.of(value));
        }
        values.add(singleRow);
        return values;
    }

    private void prepareAndWriteToSheet(T t) throws IllegalAccessException, IOException {
        List<List<Object>> values = getColumns(t);
        ValueRange body = new ValueRange().setValues(values);
        sheetsService.spreadsheets().values()
                .append(sheetId, gSheetRange, body)
                .setValueInputOption("RAW")
                .execute();
    }

    private List<List<Object>> getColumns(T t) throws IllegalAccessException {
        if (Objects.nonNull(t.getColumnsForSheet()) &&
                !t.getColumnsForSheet().isEmpty()) {
            return List.of(t.getColumnsForSheet());
        } else {
            return generateValuesFromObject(t);
        }
    }

}
