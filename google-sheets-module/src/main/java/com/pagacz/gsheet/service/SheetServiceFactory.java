package com.pagacz.gsheet.service;

import com.pagacz.database.dto.FlatOfferDto;
import com.pagacz.gsheet.exception.SheetNotFoundException;
import com.pagacz.gsheet.mapper.SheetMapperFactory;
import com.pagacz.gsheet.utils.SheetProps;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SheetServiceFactory {

    public static <T> GoogleSheetService createService(Class<T> clazz) throws GeneralSecurityException, IOException, SheetNotFoundException {
        if (clazz.equals(FlatOfferDto.class)) {
            return new GoogleSheetService<>(SheetProps.FF_CRED_PATH, SheetProps.FF_APP_NAME, SheetProps.FF_SHEET_ID, SheetProps.FF_SHEET_RANGE, SheetMapperFactory.createMapper(FlatOfferDto.class));
        }
        throw new SheetNotFoundException("There are no google sheet data for: " + clazz.getPackage() + clazz.getName());
    }
}
