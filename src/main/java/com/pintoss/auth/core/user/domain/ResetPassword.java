package com.pintoss.auth.core.user.domain;

import lombok.Getter;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ResetPassword {

    private static final int PASSWORD_LENGTH = 10;

    private static final Map<String, String> charSets = Map.of(
            "UPPER", "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
            "LOWER", "abcdefghijklmnopqrstuvwxyz",
            "DIGITS", "0123456789",
            "SYMBOLS", "!@#$%^&*"
    );

    private static final String ALL_CHARS = String.join("", charSets.values());

    @Getter
    private final String resetPassword;

    public ResetPassword(SecureRandom random) {
        List<Character> passwordChars = new ArrayList<>();

        for (String value : charSets.values()) {
            passwordChars.add(randomChar(value, random));
        }

        for (int i = passwordChars.size(); i < PASSWORD_LENGTH; i++) {
            passwordChars.add(randomChar(ALL_CHARS, random));
        }

        Collections.shuffle(passwordChars);

        StringBuilder password = new StringBuilder();
        passwordChars.forEach(password::append);
        resetPassword = password.toString();
    }

    private char randomChar(String source, SecureRandom random) {
        return source.charAt(random.nextInt(source.length()));
    }
}
