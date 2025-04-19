//package com.pintoss.auth.common.security;
//
//import com.pintoss.auth.module.user.process.domain.User;
//import lombok.AccessLevel;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//public final class SecurityContextUtils {
//
//    public static boolean isAuthenticated() {
//        return SecurityContextHolder.getContext().getAuthentication() != null
//            && SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
//    }
//
//    public static Long getUserId() {
//        User user = getUser();
//        return user == null ? null : user.getId();
//    }
//
//    public static User getUser() {
//        return SecurityContextHolder.getContext().getAuthentication() != null ?
//            ((CustomAuthentication) SecurityContextHolder.getContext().getAuthentication()).getUser() :
//            null;
//    }
//}
