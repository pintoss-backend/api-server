package com.pintoss.auth.api.support.interceptor;

import com.pintoss.auth.api.support.security.SecurityContextUtils;
import com.pintoss.auth.api.support.security.jwt.JwtAuthenticationToken;
import com.pintoss.auth.core.shared.cache.CacheType;
import com.pintoss.auth.core.shared.cache.CoreCacheManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AccessTimeTrackerInterceptor implements HandlerInterceptor {

    private final CoreCacheManager coreCacheManager;

    public AccessTimeTrackerInterceptor(CoreCacheManager coreCacheManager) {
        this.coreCacheManager = coreCacheManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        JwtAuthenticationToken jwtAuthentication = SecurityContextUtils.getJwtAuthentication();

        if(jwtAuthentication != null && jwtAuthentication.isAuthenticated()) {
            String cacheName = CacheType.AUTH_TOKEN_CACHE.getCacheName();
            Long cacheKey = jwtAuthentication.getId();

            coreCacheManager.put(cacheName, cacheKey, System.currentTimeMillis());
        }
        return true;
    }

}
