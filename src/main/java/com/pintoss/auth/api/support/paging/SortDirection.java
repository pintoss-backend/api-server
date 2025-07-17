package com.pintoss.auth.api.support.paging;

public enum SortDirection {
    ASC,
    DESC;

    public boolean isAcs() {
        return this == ASC;
    }
}
