package com.pintoss.auth.api.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtParser {

    private final JwtProvider jwtProvider;

    @Autowired
    public JwtParser(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(jwtProvider.getKey())
            .build()
            .parseClaimsJws(token)// 만료기간 포함하여 유효성 검사 진행
            .getBody();
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

}
