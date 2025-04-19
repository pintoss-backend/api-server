package com.pintoss.auth.module.user.process;

import com.pintoss.auth.module.user.infra.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenProvider {
    private final JwtProvider jwtProvider;

    public String extractSubject(String token){
        return jwtProvider.getSubject(token);
    }

    public String generateToken(String subject, boolean isRefreshToken) {
        if (isRefreshToken) {
            return jwtProvider.createRefreshToken(subject);
        } else {
            return jwtProvider.createAccessToken(subject);
        }
    }
}
