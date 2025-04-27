package com.pintoss.auth.module.user.usecase.service;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.common.security.jwt.JwtParser;
import com.pintoss.auth.common.security.jwt.JwtProvider;
import com.pintoss.auth.common.security.jwt.JwtValidator;
import com.pintoss.auth.module.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenService {

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
