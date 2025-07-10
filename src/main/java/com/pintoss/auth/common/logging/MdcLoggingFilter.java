package com.pintoss.auth.common.logging;

import com.pintoss.auth.api.security.SecurityContextUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class MdcLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            MDC.put("requestId", UUID.randomUUID().toString());
            MDC.put("userId", Optional.ofNullable(SecurityContextUtils.getUserId()).map(String::valueOf).orElse("anonymous"));
            MDC.put("app_name", "pintoss-service");
            MDC.put("env", "prod");
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
