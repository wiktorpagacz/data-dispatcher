package com.pagacz.gsheet.mapper;

import java.util.List;

public interface SheetMapper<T> {
        T map(List<Object> objects);
}
