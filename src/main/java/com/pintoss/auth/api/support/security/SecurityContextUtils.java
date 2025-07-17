package com.pintoss.auth.api.support.security;

import com.pintoss.auth.api.support.security.jwt.JwtAuthenticationToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
* SecurityContext에 저장된 인증 정보를 다루는 유틸리티 클래스
* */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityContextUtils {

    /**
    * 현재 사용자가 인증된 상태인지 확인하는 메서드
    *
    * @return 인증 여부 ( true: 인증 성공, false : 인증 실패 )
    * */
    public static boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null
            && SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    /**
    * 현재 인증된 사용자의 ID를 반환
    *
    * @return 사용자 ID, 인증되지 않은 경우 null
    * */
    public static Long getUserId() {
        JwtAuthenticationToken jwtAuthentication = getJwtAuthentication();
        return jwtAuthentication == null ? null : jwtAuthentication.getId();
    }

    /**
    * 현재 인증된 사용자의 이름을 반환합니다.
    *
    * @return 사용자 이름, 인증되지 않은 경우 null
    * */
    public static String getName(){
        JwtAuthenticationToken jwtAuthentication = getJwtAuthentication();
        return jwtAuthentication == null ? null : jwtAuthentication.getName();
    }

    /**
    * 현재 인증된 사용자의 이메일을 반환합니다.
    *
    * @return 사용자 이메일, 인증되지 않은 경우 null
     */
    public static String getEmail() {
        JwtAuthenticationToken jwtAuthentication = getJwtAuthentication();
        return jwtAuthentication == null ? null : jwtAuthentication.getEmail();
    }


    /**
     * 현재 인증된 사용자의 전화번호를 반환합니다.
     *
     * @return 사용자 전화번호, 인증되지 않은 경우 null
     */
    public static String getPhone() {
        JwtAuthenticationToken jwtAuthentication = getJwtAuthentication();
        return jwtAuthentication != null ? jwtAuthentication.getPhone() : null;
    }
    /**
     * 현재 SecurityContext에 저장된 Authentication 객체를 JwtAuthenticationToken으로 변환하여 반환합니다.
     *
     * @return JwtAuthenticationToken 객체, 타입이 다르면 null
     */
    public static JwtAuthenticationToken getJwtAuthentication() {
        Authentication authentication = getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            return (JwtAuthenticationToken) authentication;
        }
        return null;
    }

    /**
     * 현재 SecurityContext에 저장된 Authentication 객체를 반환합니다.
     *
     * @return Authentication 객체, 없으면 null
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * SecurityContext에 저장된 인증 정보를 초기화합니다.
     * (ex: 로그아웃 처리 등)
     */
    public static void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }
}
