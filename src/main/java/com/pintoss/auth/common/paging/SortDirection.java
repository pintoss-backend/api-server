package com.pintoss.auth.common.paging;

public enum SortDirection {
    ASC,
    DESC;

    public boolean isAcs() {
        return this == ASC;
    }
}
