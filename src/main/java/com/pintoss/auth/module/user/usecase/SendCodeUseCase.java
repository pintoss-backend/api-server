package com.pintoss.auth.module.user.usecase;

import com.pintoss.auth.module.user.model.Phone;
import com.pintoss.auth.module.user.model.User;
import com.pintoss.auth.module.user.usecase.service.MailSender;
import com.pintoss.auth.module.user.usecase.service.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendCodeUseCase {

    private final MailSender mailSender;
    private final UserValidator userValidator;

    public void sendCode(String email, String name, String phone) {
        User user = userValidator.validateExistsBy(email, name, new Phone(phone));

        mailSender.sendVerificationCodeForPasswordReset(email);
    }
}
