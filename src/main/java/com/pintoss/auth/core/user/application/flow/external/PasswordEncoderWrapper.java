package com.pintoss.auth.core.user.application.flow.external;

public interface PasswordEncoderWrapper {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
