package com.pintoss.auth.api.support.interceptor;

import com.pintoss.auth.support.exception.ForbiddenException;
import com.pintoss.auth.support.exception.UnauthorizedException;
import com.pintoss.auth.core.user.domain.UserRoleEnum;
import com.pintoss.auth.support.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        AuthorizationRequired annotation = getAnnotation(handler);

        if( annotation == null ){
            return true;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException(annotation.failureMessage());
        }

        Collection<? extends GrantedAuthority> requiredAuthorities = roleToAuthority(annotation.value());

        boolean hasRequiredRole = authentication.getAuthorities().stream()
                .anyMatch(requiredAuthorities::contains);

        if (!hasRequiredRole) {
            throw new ForbiddenException(ErrorCode.AUTH_ACCESS_DENIED);
        }

        return true;
    }

    private AuthorizationRequired getAnnotation(Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return null; // HandlerMethod가 아니면 null 반환
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        return handlerMethod.getMethodAnnotation(AuthorizationRequired.class);
    }

    private Collection<? extends GrantedAuthority> roleToAuthority(UserRoleEnum[] required) {
        return Arrays.stream(required)
            .map(Enum::name)
            .map(SimpleGrantedAuthority::new)
            .toList();
    }
}

