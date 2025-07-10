package com.pintoss.auth.api.common.util;

import static java.util.Optional.empty;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class HttpServletUtils {

    public Optional<String> getAccessToken(HttpServletRequest request) {
        Optional<String> authorization = Optional.ofNullable(request.getHeader("Authorization"));

        if(authorization.isPresent()) {
            if(authorization.get().startsWith("Bearer ")) {
                return Optional.of(authorization.get().substring(7));
            }
        }
        return authorization;
    }
    public Optional<String> getHeader(HttpServletRequest request, String name) {
        return Optional.ofNullable(request.getHeader(name));
    }

    public void putHeader(HttpServletResponse response, String name, String value) {
        response.addHeader(name, value);
    }

    public void addCookie(HttpServletResponse response, String name, String value, int seconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(seconds);
        cookie.setSecure(true);
        response.addHeader("SameSite", "None");
        response.addCookie(cookie);
    }

    public void removeCookie(HttpServletRequest request, HttpServletResponse response,
        String name) {
        if (getCookie(request, name).isPresent()) {
            Cookie cookie = new Cookie(name, "");
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            cookie.setSecure(true);
            response.addHeader("SameSite", "None");
            response.addCookie(cookie);
        }
    }
    public Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(name))
                .findAny();
        }

        return empty();
    }

}


