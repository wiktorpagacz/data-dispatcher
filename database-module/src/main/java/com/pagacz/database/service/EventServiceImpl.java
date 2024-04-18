package com.pagacz.database.service;

import com.pagacz.kafka.listener.model.generated.FlatData;
import com.pagacz.kafka.listener.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    private final OfferService offerService;

    @Autowired
    public EventServiceImpl(OfferService offerService) {
        this.offerService = offerService;
    }

    @Override
    public void save(FlatData flatData) {
        offerService.saveFlatDataAsOffer(flatData);
    }
}
