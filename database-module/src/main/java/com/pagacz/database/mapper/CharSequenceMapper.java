package com.pagacz.database.mapper;

import org.mapstruct.Mapper;

@Mapper
public interface CharSequenceMapper {
    default String map(CharSequence charSequence) {
        return charSequence.toString();
    }

    default CharSequence map(String string) {
        return string;
    }
}
