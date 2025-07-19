package com.pintoss.auth.api.support.api;

import com.pintoss.auth.api.support.interceptor.AuthorizationInterceptor;
import com.pintoss.auth.api.support.filter.MdcLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor())
                .excludePathPatterns("/swagger-ui/**");
    }

    @Bean
    AuthorizationInterceptor authorizationInterceptor() {
        return new AuthorizationInterceptor();
    }
}
