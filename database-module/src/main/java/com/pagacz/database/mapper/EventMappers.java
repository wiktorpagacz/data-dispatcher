package com.pagacz.database.mapper;

import com.pagacz.database.model.FlatOffer;
import com.pagacz.kafka.listener.model.generated.FlatData;
import org.mapstruct.Mapper;

@Mapper(uses = CharSequenceMapper.class)
public interface EventMappers {

    FlatOffer flatDataToOffer(FlatData flatData);
}
