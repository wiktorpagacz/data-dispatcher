package com.pagacz.database.mapper;

import com.pagacz.database.dto.FlatOfferDto;
import com.pagacz.database.model.FlatOffer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DtoMappers {

    FlatOfferDto offerToDto(FlatOffer offer);

}
