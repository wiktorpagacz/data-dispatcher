package com.pagacz.database.service;

import com.pagacz.database.dto.FlatOfferDto;
import com.pagacz.database.mapper.DtoMappers;
import com.pagacz.database.mapper.EventMappers;
import com.pagacz.database.model.FlatOffer;
import com.pagacz.database.repository.FlatOfferRepository;
import com.pagacz.kafka.listener.model.generated.FlatData;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    EventMappers eventMappers = Mappers.getMapper(EventMappers.class);
    FlatOfferRepository flatOfferRepository;
    DtoMappers dtoMappers;

    @Autowired
    public OfferServiceImpl(FlatOfferRepository flatOfferRepository, DtoMappers dtoMappers) {
        this.flatOfferRepository = flatOfferRepository;
        this.dtoMappers = dtoMappers;
    }

    @Override
    public List<FlatOffer> getOffersToSend() {
        return flatOfferRepository.getOffersBySendByEmailStatus('N');
    }

    @Override
    public void markOfferSendAsError(List<FlatOffer> offersToSend) {
        offersToSend.forEach(o -> o.setSendByEmail('N'));
        flatOfferRepository.saveAll(offersToSend);
    }

    @Override
    public void markOfferSendAsSuccessful(List<FlatOffer> offersToSend, LocalDateTime sendTime) {
        offersToSend.forEach(o -> o.setSendByEmail('N'));
        offersToSend.forEach(o -> o.setSendByEmailTime(sendTime));
        flatOfferRepository.saveAll(offersToSend);
    }

    @Override
    public List<FlatOffer> getNotWroteOffers() {
        return flatOfferRepository.getOffersByWriteToDocsStatus('N');
    }

    @Override
    public void saveFlatDataAsOffer(FlatData flatData) {
        FlatOffer flatOffer = eventMappers.flatDataToOffer(flatData);
        flatOfferRepository.save(flatOffer);
    }

    public List<FlatOfferDto> getDtosFromOffers(List<FlatOffer> offers) {
        List<FlatOfferDto> flatOfferDtos = offers.stream()
                .map(offer -> dtoMappers.offerToDto(offer))
                .collect(Collectors.toList());
        flatOfferDtos.forEach(o -> o.setInsertDate(LocalDateTime.now()));
        for (FlatOfferDto offer : flatOfferDtos) {
            if (offer.getComment().isEmpty()) {
                offer.setComment("-");
            }
        }
        return flatOfferDtos;
    }

    @Override
    public void saveOffers(List<FlatOffer> offers) {
        flatOfferRepository.saveAll(offers);
    }
}
