package com.pintoss.auth.core.support.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        List<Cache> caches = List.of(CacheType.values()).stream()
                .map(cacheType -> new CaffeineCache(
                        cacheType.getCacheName(),
                        Caffeine.newBuilder()
                                .expireAfterWrite(cacheType.getExpireAfterWriteMinutes(), TimeUnit.MINUTES)
                                .maximumSize(cacheType.getMaximumSize())
                                .build()
                )).collect(Collectors.toList());
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    @Bean
    public CacheManagerWrapper coreCacheManager(CacheManager cacheManager) {
        return new CacheManagerWrapper(cacheManager);
    }

}
