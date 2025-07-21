package com.pintoss.auth.core.user.application.flow.validator;

import com.pintoss.auth.core.exception.CoreErrorCode;
import com.pintoss.auth.core.exception.CoreException;
import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.api.support.exception.client.BadRequestException;
import com.pintoss.auth.core.user.application.flow.external.PasswordEncoderWrapper;
import com.pintoss.auth.core.user.application.repository.UserRepository;
import com.pintoss.auth.core.user.domain.Phone;
import com.pintoss.auth.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;
    private final PasswordEncoderWrapper passwordEncoder;

    public void duplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException(ErrorCode.DUPLICATE_USER);
        }
    }

    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean isPhoneDuplicate(Phone phone) {
        return userRepository.existsByPhone(phone);
    }

    public void matchPassword(String rawPassword, String inputPassword) {
        if(!passwordEncoder.matches(rawPassword, inputPassword)) {
            throw new BadRequestException(ErrorCode.AUTH_PASSWROD_INVALID);
        }
    }

    public void validateRefreshTokenOwner(String subject, Long userId) {
        if(!subject.equals(userId.toString())) {
            throw new CoreException(CoreErrorCode.INVALID_REFRESH_TOKEN_SUBJECT);
        }
    }

    public User validateExistsBy(String email, String name, Phone phone) {
        return userRepository.findByEmailAndNameAndPhone(email, name, phone)
            .orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));
    }
}
