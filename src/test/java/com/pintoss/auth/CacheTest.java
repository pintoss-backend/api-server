package com.pintoss.auth;

import com.pintoss.auth.core.support.cache.CacheType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class CacheTest {

    @Autowired
    CacheManager cacheManager;

    @Test
    @DisplayName("authTokenCache 캐시가 CacheManager에 등록 되어 있어야 한다.")
    void givenCacheManager_whenGetAuthTokenCache_thenCacheShouldExist() {
        // Given
        String cacheName = "authTokenCache";

        // When
        Cache cache = cacheManager.getCache(cacheName);

        // Then
        Assertions.assertThat(cache).isNotNull();
    }

    @Test
    @DisplayName("정의되지 않은 캐시 이름을 요청하면 null이 반환되어야한다.")
    void shouldReturnNull_whenUnknownCacheRequested() {
        // when
        Cache cache = cacheManager.getCache("unknownCache");

        // then
        Assertions.assertThat(cache).isNull();
    }

    @Test
    void existingTest() {
        Cache cache = cacheManager.getCache(CacheType.AUTH_TOKEN_CACHE.getCacheName());
        cache.put(1L, "testToken");
        Object o = cache.get(1L).get();
        Object o1 = cache.get(2L);

        System.out.println(o);
        System.out.println(o1);
    }
}
