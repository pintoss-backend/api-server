package com.pintoss.auth.api.support.config;

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

    @Bean
    public FilterRegistrationBean<MdcLoggingFilter> mdcFilterRegistration() {
        FilterRegistrationBean<MdcLoggingFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new MdcLoggingFilter());
        registration.setOrder(Ordered.LOWEST_PRECEDENCE); // 거의 마지막에 실행
        registration.addUrlPatterns("/*");
        return registration;
    }
}
