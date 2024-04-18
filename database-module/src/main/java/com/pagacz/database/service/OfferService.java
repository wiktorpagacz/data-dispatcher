package com.pagacz.database.service;

import com.pagacz.database.dto.FlatOfferDto;
import com.pagacz.database.model.FlatOffer;
import com.pagacz.kafka.listener.model.generated.FlatData;

import java.time.LocalDateTime;
import java.util.List;

public interface OfferService {

    void saveFlatDataAsOffer(FlatData offer);
    void markOfferSendAsError(List<FlatOffer> offersToSend);
    void markOfferSendAsSuccessful(List<FlatOffer> offersToSend, LocalDateTime sendTime);
    List<FlatOffer> getNotWroteOffers();
    List<FlatOffer> getOffersToSend();
    List<FlatOfferDto> getDtosFromOffers(List<FlatOffer> flatOffers);
    void saveOffers(List<FlatOffer> offers);
}
