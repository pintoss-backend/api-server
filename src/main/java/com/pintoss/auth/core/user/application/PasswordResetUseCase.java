package com.pintoss.auth.core.user.application;

import com.pintoss.auth.core.user.application.flow.external.MailSender;
import com.pintoss.auth.core.user.application.flow.reader.UserReader;
import com.pintoss.auth.core.user.domain.LoginType;
import com.pintoss.auth.core.user.domain.Phone;
import com.pintoss.auth.core.user.domain.ResetPassword;
import com.pintoss.auth.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class PasswordResetUseCase {

    private final UserReader userReader;
    private final PasswordEncoder encoder;
    private final MailSender mailSender;
    private final SecureRandom secureRandom;

    @Transactional
    public void passwordReset(String email, String name, String phone) {
        User user = userReader.readByEmailAndNameAndPhoneAndLoginType(email, name, new Phone(phone),
            LoginType.LOCAL);

        // 임시 비밀번호
        String tempPassword = new ResetPassword(secureRandom).getResetPassword();

        // 메일 전송 ( 비동기 )
        mailSender.sendTemporaryPassword(email, tempPassword);
        
        user.resetPassword(encoder.encode(tempPassword));
    }

}
