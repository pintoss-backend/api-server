package com.pintoss.auth.core.user.application.flow.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PasswordGenerator {

    private static final int PASSWORD_LENGTH = 10;

    private static final Map<String, String> charSets = Map.of(
            "UPPER", "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
            "LOWER", "abcdefghijklmnopqrstuvwxyz",
            "DIGITS", "0123456789",
            "SYMBOLS", "!@#$%^&*"
    );

    private static final String ALL_CHARS = String.join("", charSets.values());

    private final SecureRandom random;

    public String generate() {
        List<Character> passwordChars = new ArrayList<>();

        addCharSets(passwordChars);
        addRemainCharSets(passwordChars);
        Collections.shuffle(passwordChars, random);

        StringBuilder password = new StringBuilder();
        passwordChars.forEach(password::append);
        
        return password.toString();
    }

    private void addRemainCharSets(List<Character> passwordChars) {
        for (int i = passwordChars.size(); i < PASSWORD_LENGTH; i++) {
            passwordChars.add(randomChar(ALL_CHARS));
        }
    }

    private void addCharSets(List<Character> passwordChars) {
        for (String value : charSets.values()) {
            passwordChars.add(randomChar(value));
        }
    }

    private char randomChar(String source) {
        return source.charAt(random.nextInt(source.length()));
    }
}
