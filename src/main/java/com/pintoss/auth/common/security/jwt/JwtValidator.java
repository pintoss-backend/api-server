package com.pintoss.auth.common.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    private final JwtParser jwtParser;

    @Autowired
    public JwtValidator(JwtParser jwtParser) {
        this.jwtParser = jwtParser;
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
