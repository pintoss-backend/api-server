package com.pintoss.auth.api.support.api;

import com.pintoss.auth.api.support.interceptor.AccessTimeTrackerInterceptor;
import com.pintoss.auth.api.support.interceptor.AuthorizationInterceptor;
import com.pintoss.auth.core.support.cache.CacheManagerWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {

    private final CacheManagerWrapper cacheManagerWrapper;

    public WebInterceptorConfig(CacheManagerWrapper cacheManagerWrapper) {
        this.cacheManagerWrapper = cacheManagerWrapper;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor())
                .excludePathPatterns("/swagger-ui/**");

        registry.addInterceptor(accessTimeTrackerInterceptor())
                .excludePathPatterns("/swagger-ui/**");
    }

    @Bean
    AccessTimeTrackerInterceptor accessTimeTrackerInterceptor() {
        return new AccessTimeTrackerInterceptor(cacheManagerWrapper);
    }

    @Bean
    AuthorizationInterceptor authorizationInterceptor() {
        return new AuthorizationInterceptor();
    }
}
