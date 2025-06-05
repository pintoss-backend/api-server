package com.pintoss.auth.module.user.application;

import com.pintoss.auth.module.user.core.UserValidator;
import com.pintoss.auth.module.user.domain.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidationUseCase {

    private final UserValidator userValidator;

    public Boolean checkEmailDuplicate(String email) {
        return userValidator.isEmailDuplicate(email);
    }

    public Boolean checkPhoneDuplicate(String phoneValue) {
        Phone phone = new Phone(phoneValue);
        return userValidator.isPhoneDuplicate(phone);
    }

}
