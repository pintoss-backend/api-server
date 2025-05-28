package com.pintoss.auth.common.paging;

import java.util.List;
import lombok.Getter;

@Getter
public class PagedData<T> {

    private final List<T> items;
    private final long totalElements;

    public PagedData(List<T> items, long totalElements) {
        this.items = items;
        this.totalElements = totalElements;
    }
}
