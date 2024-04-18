package com.pagacz.kafka.listener.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagacz.kafka.listener.model.generated.FlatData;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class FlatDataDeserializer implements Deserializer<FlatData> {
    private ObjectMapper objectMapper = new ObjectMapper();
    private final Logger log = LoggerFactory.getLogger(FlatDataDeserializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public FlatData deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                log.debug("Null received at deserializing");
                return null;
            }
            log.debug("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), FlatData.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }

    @Override
    public void close() {
    }
}
