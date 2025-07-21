package com.pintoss.auth.api.support.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

    public Claims getClaimsAllowExpired(String token) {
        try {
            return getClaims(token);
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getSubjectAllowExpired(String token) {
        return getClaimsAllowExpired(token).getSubject();
    }

}
