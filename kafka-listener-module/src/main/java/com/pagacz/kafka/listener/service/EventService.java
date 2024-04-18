package com.pagacz.kafka.listener.service;

import com.pagacz.kafka.listener.model.generated.FlatData;
import org.springframework.stereotype.Service;

@Service
public interface EventService {
    void save(FlatData flatData);
}
