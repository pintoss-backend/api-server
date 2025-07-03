package com.pintoss.auth.core.user.core;

import java.util.Optional;

public interface PasswordVerifyCodeStore {

    void save(String email, String code);
    Optional<String> get(String email);
    void remove(String email);
    boolean verify(String email, String code);

}
