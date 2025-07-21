package com.pintoss.auth.cache;

import com.pintoss.auth.api.support.exception.server.InternalServerException;
import com.pintoss.auth.core.shared.cache.CoreCacheManager;
import com.pintoss.auth.support.exception.ErrorCode;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class CacheManagerImpl implements CoreCacheManager {

    private final CacheManager cacheManager;

    public CacheManagerImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public <T> void put(String cacheName, Object key, T value) {
        try {
            getCacheOrThrow(cacheName).put(key, value);
        } catch (Exception e) {
            throw new InternalServerException(ErrorCode.CACHE_ACCESS_ERROR);
        }
    }

    @Override
    public <T> T get(String cacheName, Object key, Class<T> type) {
        try {
            return getCacheOrThrow(cacheName).get(key, type);
        } catch (Exception e) {
            throw new InternalServerException(ErrorCode.CACHE_ACCESS_ERROR);
        }
    }

    @Override
    public void evict(String cacheName, Object key) {
        try {
            getCacheOrThrow(cacheName).evict(key);
        } catch (Exception e) {
            throw new InternalServerException(ErrorCode.CACHE_ACCESS_ERROR);
        }
    }

    @Override
    public boolean exists(String cacheName, Object key) {
        try {
            Cache cache = getCacheOrThrow(cacheName);
            return cache.get(key) != null;
        } catch (Exception e) {
            throw new InternalServerException(ErrorCode.CACHE_ACCESS_ERROR);
        }
    }

    private Cache getCacheOrThrow(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            throw new InternalServerException(ErrorCode.CACHE_NOT_FOUND);
        }
        return cache;
    }
}
