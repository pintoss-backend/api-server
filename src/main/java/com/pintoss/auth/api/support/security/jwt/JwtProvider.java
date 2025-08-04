package com.pintoss.auth.api.support.security.jwt;

import com.pintoss.auth.core.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;
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

    public String createAccessToken(User user) {
        return createToken(user, properties.getAccessTokenExpiration());
    }

    public String createRefreshToken(User user) {
        return createToken(user, properties.getRefreshTokenExpiration());
    }

    /**
     * Constructs JWT claims for the specified user, including subject, issued and expiration times, and user details.
     *
     * @param user   the user whose information will be included in the claims
     * @param millis the duration in milliseconds until the token expires
     * @return a Claims object populated with user ID, email, name, phone number, and roles
     */
    private Claims createClaims(User user, long millis) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + millis + 1000L);
        Claims claims = Jwts.claims()
            .setSubject(String.valueOf(user.getId()))
            .setIssuedAt(now)
            .setExpiration(expireDate);

        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());
        claims.put("phone", user.getPhone().getPhone());
        claims.put("roles", user.getRoles()
            .stream()
            .map(role -> role.getName().name()).collect(Collectors.toSet()));
        return claims;
    }

    private String createToken(User user, long millis) {
        Claims claims = createClaims(user, millis);

        return Jwts.builder()
            .setClaims(claims)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public Key getKey() {
        return key;
    }
}


