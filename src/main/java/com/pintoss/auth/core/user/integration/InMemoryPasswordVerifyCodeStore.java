package com.pintoss.auth.core.user.integration;

import com.pintoss.auth.core.user.core.PasswordVerifyCodeStore;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPasswordVerifyCodeStore implements PasswordVerifyCodeStore {

    private final Map<String, String> store = new ConcurrentHashMap<>();

    @Override
    public void save(String email, String code) {
        store.put(email, code);
    }

    @Override
    public Optional<String> get(String email) {
        return Optional.ofNullable(store.get(email));
    }

    @Override
    public void remove(String email) {
        store.remove(email);
    }

    @Override
    public boolean verify(String email, String code) {
        return code.equals(store.get(email));
    }
}
