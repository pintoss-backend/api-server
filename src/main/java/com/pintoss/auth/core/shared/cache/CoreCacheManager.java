package com.pintoss.auth.core.shared.cache;

public interface CoreCacheManager {

    <T> void put(String cacheName, Object key, T value);

    <T> T get(String cacheName, Object key, Class<T> type);

    void evict(String cacheName, Object key);

    boolean exists(String cacheName, Object key);
}
