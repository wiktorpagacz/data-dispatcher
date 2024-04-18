package com.pagacz.gsheet.mapper;

import com.pagacz.database.dto.FlatOfferDto;

import java.util.List;

public class OfferMapper implements SheetMapper<FlatOfferDto> {

    @Override
    public FlatOfferDto map(List<Object> values) {
        FlatOfferDto flatOfferDto = new FlatOfferDto();
        flatOfferDto.setLink((String) values.get(0));
        flatOfferDto.setId(Long.parseLong((String) values.get(1)));
        flatOfferDto.setTitle((String) values.get(2));
        flatOfferDto.setAddress((String) values.get(3));
        flatOfferDto.setPrice(Integer.valueOf((String) values.get(4)));
        flatOfferDto.setOriginalPrice(Integer.valueOf((String) values.get(5)));
        flatOfferDto.setSpace(Double.valueOf((String) values.get(6)));
        flatOfferDto.setSource((String) values.get(7));
        flatOfferDto.setComment((String) values.get(8));
        return flatOfferDto;
    }
}
