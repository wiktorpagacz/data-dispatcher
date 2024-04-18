package com.pagacz.database.dto;

import java.util.List;

public abstract class BaseDto {

    private int index;
    public abstract List<Object> getColumnsForSheet();
    public abstract List<Object> getColumnsForMail();
    public abstract List<String> getMailTableHeaders();
    public abstract String getUniqueIdentifier();
    public int getIndex() {
        return this.index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
