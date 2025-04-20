package com.pintoss.auth.common.security;

import com.pintoss.auth.common.security.jwt.JwtAuthenticationToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityContextUtils {

    public static boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null
            && SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    public static Long getUserId() {
        JwtAuthenticationToken jwtAuthentication = getJwtAuthentication();
        return jwtAuthentication == null ? null : jwtAuthentication.getId();
    }

    public static JwtAuthenticationToken getJwtAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication() != null ?
            ((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication()) :
            null;
    }
}
