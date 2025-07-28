package com.pintoss.auth.api.support.interceptor;

import com.pintoss.auth.api.support.security.SecurityContextUtils;
import com.pintoss.auth.api.support.security.jwt.JwtAuthenticationToken;
import com.pintoss.auth.core.support.cache.CacheType;
import com.pintoss.auth.core.support.cache.CacheManagerWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AccessTimeTrackerInterceptor implements HandlerInterceptor {

    private final CacheManagerWrapper cacheManagerWrapper;

    public AccessTimeTrackerInterceptor(CacheManagerWrapper cacheManagerWrapper) {
        this.cacheManagerWrapper = cacheManagerWrapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        JwtAuthenticationToken jwtAuthentication = SecurityContextUtils.getJwtAuthentication();

        if(jwtAuthentication != null && jwtAuthentication.isAuthenticated()) {
            String cacheName = CacheType.AUTH_TOKEN_CACHE.getCacheName();
            Long cacheKey = jwtAuthentication.getId();

            cacheManagerWrapper.put(cacheName, cacheKey, System.currentTimeMillis());
        }
        return true;
    }

}
