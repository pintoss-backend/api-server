package com.pintoss.auth.module.user.infra.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private final JwtProperties properties;
    private Key key;

    @Autowired
    public JwtProvider(JwtProperties properties){
        this.properties = properties;
    }

    @PostConstruct
    void init() {
        this.key = Keys.hmacShaKeyFor(properties.getSecretKey().getBytes());
    }

    public String createAccessToken(String subject) {
        return createToken(subject, properties.getAccessTokenExpiration());
    }

    public String createRefreshToken(String subject) {
        return createToken(subject, properties.getRefreshTokenExpiration());
    }
    private Claims createClaims(String subject, long millis){
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + millis + 1000L);

        return Jwts.claims()
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(expireDate);
    }

    private String createToken(String subject, long millis) {
        Claims claims = createClaims(subject, millis);

        return Jwts.builder()
            .setClaims(claims)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public String getSubject(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }
}


