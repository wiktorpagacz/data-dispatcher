package com.pagacz.gateway.application.scheduler;

import com.pagacz.database.dto.FlatOfferDto;
import com.pagacz.database.mapper.DtoMappers;
import com.pagacz.database.model.FlatOffer;
import com.pagacz.database.service.OfferService;
import com.pagacz.gsheet.exception.SheetNotFoundException;
import com.pagacz.gsheet.service.GoogleSheetService;
import com.pagacz.gsheet.service.SheetServiceFactory;
import com.pagacz.mail.service.EmailService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final OfferService offerService;
    private DtoMappers dtoMappers = Mappers.getMapper(DtoMappers.class);

    public TaskService(OfferService offerService) {
        this.offerService = offerService;
    }

    @Transactional
    public void addOffersToGoogleSheet() {
        List<FlatOffer> offers = offerService.getNotWroteOffers();
        List<FlatOfferDto> offersDto = offerService.getDtosFromOffers(offers);
        try {
            List<FlatOfferDto> offersToUpdate = new ArrayList<>();
            GoogleSheetService<FlatOfferDto> googleSheetService = SheetServiceFactory.createService(FlatOfferDto.class);
            Map<String, FlatOfferDto> existingOffers = googleSheetService.getExistingDataFromSheet();
            splitOffersBetweenNewAndOld(offersDto, offersToUpdate, existingOffers);
            googleSheetService.updateListInSheet(offersToUpdate);
            googleSheetService.addAllData(offersDto);
            offers.forEach(o -> o.setInsertDate(LocalDateTime.now()));
            offers.forEach(o -> o.setWriteToDocs('Y'));
            offerService.saveOffers(offers);
        } catch (GeneralSecurityException | IOException e) {
            log.error(e.getMessage());
        } catch (SheetNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void sendEmailWithOffers() {
        EmailService<FlatOfferDto> emailService = new EmailService<>();
        List<FlatOffer> offersToSend = offerService.getOffersToSend();
        List<FlatOfferDto> flatOfferDtos = offersToSend.stream()
                .map(offer -> dtoMappers.offerToDto(offer))
                .collect(Collectors.toList());
        LocalDateTime sendTime = LocalDateTime.now();
        try {
            emailService.prepareAndSendMailWithOffers(flatOfferDtos);
            offerService.markOfferSendAsSuccessful(offersToSend, sendTime);
        } catch (Exception e) {
            log.error("Exception occurred at prepare and send mail stage: ", e);
            offerService.markOfferSendAsError(offersToSend);
        }
    }

    private void splitOffersBetweenNewAndOld(List<FlatOfferDto> flatOfferDtos, List<FlatOfferDto> offersToUpdate, Map<String, FlatOfferDto> existingOffers) {
        flatOfferDtos.forEach(offerDto ->{
            FlatOfferDto oldOffer = existingOffers.get(offerDto.getLink());
            if (oldOffer != null) {
                if (Objects.equals(offerDto.getPrice(), oldOffer.getPrice())) {
                    oldOffer.setComment("Nowa cena, poprzednia: " + oldOffer.getPrice());
                    oldOffer.setPrice(offerDto.getPrice());
                }
                offersToUpdate.add(oldOffer);
            }
        });
        offersToUpdate.forEach(offerToUpdate -> flatOfferDtos.removeIf(offerDto -> offerDto.getLink().equals(offerToUpdate.getLink())));
    }
}
