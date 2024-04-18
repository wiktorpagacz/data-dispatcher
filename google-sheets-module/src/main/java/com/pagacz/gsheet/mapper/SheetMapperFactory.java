package com.pagacz.gsheet.mapper;

import com.pagacz.database.dto.FlatOfferDto;

public class SheetMapperFactory {
    public static <T> SheetMapper<T> createMapper(Class<T> clazz) {
        if (clazz.equals(FlatOfferDto.class)) {
            return (SheetMapper<T>) new OfferMapper();
        }
        return null;
    }
}
