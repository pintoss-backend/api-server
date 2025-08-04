package com.pintoss.auth.core.user.application;

import com.pintoss.auth.core.user.application.flow.external.MailSender;
import com.pintoss.auth.core.user.application.flow.processor.PasswordGenerator;
import com.pintoss.auth.core.user.application.flow.reader.UserReader;
import com.pintoss.auth.core.user.domain.LoginType;
import com.pintoss.auth.core.user.domain.Phone;
import com.pintoss.auth.core.user.domain.User;
import com.pintoss.auth.core.user.domain.vo.EncodedPassword;
import com.pintoss.auth.core.user.domain.vo.Password;
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
    private final PasswordGenerator passwordGenerator;

    @Transactional
    public void passwordReset(String email, String name, String phone) {
        User user = userReader.readByEmailAndNameAndPhoneAndLoginType(email, name, new Phone(phone),
            LoginType.LOCAL);

        // 임시 비밀번호
        String tmpPassword = passwordGenerator.generate();
        Password password = new Password(tmpPassword);
        EncodedPassword encodedPassword = new EncodedPassword(encoder.encode(tmpPassword));

        // 메일 전송 ( 비동기 )
        mailSender.sendTemporaryPassword(email, password);
        
        user.resetPassword(encodedPassword);
    }

}
