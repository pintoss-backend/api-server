package com.pintoss.auth.cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.pintoss.auth.core.shared.cache.CacheType;
import com.pintoss.auth.core.shared.cache.CoreCacheManager;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    public CoreCacheManager coreCacheManager(CacheManager cacheManager) {
        return new CacheManagerImpl(cacheManager);
    }

}
