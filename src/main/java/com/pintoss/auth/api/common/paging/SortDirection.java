package com.pintoss.auth.api.common.paging;

public enum SortDirection {
    ASC,
    DESC;

    public boolean isAcs() {
        return this == ASC;
    }
}
