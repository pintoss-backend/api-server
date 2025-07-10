package com.pintoss.auth.core.user.application;

import com.pintoss.auth.core.user.domain.LoginType;
import com.pintoss.auth.core.user.domain.Phone;
import com.pintoss.auth.core.user.domain.User;
import com.pintoss.auth.core.user.application.flow.external.MailSender;
import com.pintoss.auth.core.user.application.flow.reader.UserReader;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PasswordResetUseCase {

    private final UserReader userReader;
    private final PasswordEncoder encoder;
    private final MailSender mailSender;

    @Transactional
    public void passwordReset(String email, String name, String phone) {
        User user = userReader.readByEmailAndNameAndPhoneAndLoginType(email, name, new Phone(phone),
            LoginType.LOCAL);
        // 임시 비밀번호
        String tempPassword = generateTemporaryPassword();
        // 메일 전송 ( 비동기 )
        mailSender.sendTemporaryPassword(email, tempPassword);
        
        user.resetPassword(encoder.encode(tempPassword));
    }

    private String generateTemporaryPassword() {
        int length = 10;
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String symbols = "!@#$%^&*";

        String allChars = upper + lower + digits + symbols;
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        // 보안상 최소 1개씩은 반드시 포함
        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(symbols.charAt(random.nextInt(symbols.length())));

        // 나머지 자리 채우기
        for (int i = 4; i < length; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // 순서 섞기
        List<Character> chars = password.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.toList());
        Collections.shuffle(chars);

        return chars.stream()
            .map(String::valueOf)
            .collect(Collectors.joining());
    }

}
