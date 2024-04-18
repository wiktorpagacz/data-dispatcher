package service;

import com.pagacz.database.dto.FlatOfferDto;
import com.pagacz.database.mapper.DtoMappers;
import com.pagacz.database.model.FlatOffer;
import com.pagacz.mail.service.MailCreatorService;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MailCreatorServiceTest {

    DtoMappers dtoMappers = Mappers.getMapper(DtoMappers.class);

    @Test
    void createMailFromOffersTest() throws IOException {
        List<FlatOffer> dataItems = Arrays.asList(
                new FlatOffer("https://test-link.com/offeraddress", "Title test", "Test page title", 359000, 330000, 59.4, "test address"),
                new FlatOffer("https://test-link.com/offeraddress2", "Title test 2", "Other Test page title", 120000, 130000, 32.1, "other test address")
        );

        List<FlatOfferDto> offerDtos = new ArrayList<>();
        for (FlatOffer dataItem : dataItems) {
            offerDtos.add(dtoMappers.offerToDto(dataItem));
        }

        MailCreatorService<FlatOfferDto> mailCreatorService = new MailCreatorService();
        String generatedMail = mailCreatorService.createMailFromOffers(offerDtos);

        File file = new File("src/test/resources/generated-mail-content.txt");
        String preparedMail = FileUtils.readFileToString(file, "UTF-8");

        Assertions.assertEquals(preparedMail, generatedMail);
    }
}
