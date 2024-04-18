package com.pagacz.kafka.listener;

import com.pagacz.kafka.listener.model.generated.FlatData;
import com.pagacz.kafka.listener.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageHandler {

    @Autowired
    EventService eventService;

    @KafkaListener(topics = "flat-write", groupId = "data-dispatcher-group")
    public void listenOffer(FlatData message) {
        eventService.save(message);
    }
}