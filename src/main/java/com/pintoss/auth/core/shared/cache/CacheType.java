package com.pintoss.auth.core.shared.cache;

import lombok.Getter;

@Getter
public enum CacheType {
    AUTH_TOKEN_CACHE("authTokenCache", 30, 1000);

    private final String cacheName;
    private final int expireAfterWriteMinutes;
    private final int maximumSize;

    CacheType(String cacheName, int expireAfterWriteMinutes, int maximumSize) {
        this.cacheName = cacheName;
        this.expireAfterWriteMinutes = expireAfterWriteMinutes;
        this.maximumSize = maximumSize;
    }
}
