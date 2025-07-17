package com.pintoss.auth.core.user.application.flow.processor;

import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.api.support.exception.client.BadRequestException;
import com.pintoss.auth.api.support.security.jwt.JwtParser;
import com.pintoss.auth.api.support.security.jwt.JwtProvider;
import com.pintoss.auth.api.support.security.jwt.JwtValidator;
import com.pintoss.auth.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenProcessor {

    private final JwtProvider jwtProvider;
    private final JwtParser jwtParser;
    private final JwtValidator jwtValidator;

    public String extractSubject(String token){
        verifyToken(token);
        return jwtParser.getSubject(token);
    }

    public String generateToken(User user, boolean isRefreshToken) {
        if (isRefreshToken) {
            return jwtProvider.createRefreshToken(user);
        } else {
            return jwtProvider.createAccessToken(user);
        }
    }

    public void verifyToken(String refreshToken) {
        if(!jwtValidator.validateToken(refreshToken)) {
            throw new BadRequestException(ErrorCode.AUTH_INVALID_REFRESH_TOKEN);
        }
    }
}
