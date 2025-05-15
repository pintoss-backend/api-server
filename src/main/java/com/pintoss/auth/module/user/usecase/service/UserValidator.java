package com.pintoss.auth.module.user.usecase.service;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.common.exception.client.DuplicateEmailException;
import com.pintoss.auth.common.security.SecurityContextUtils;
import com.pintoss.auth.module.user.model.Phone;
import com.pintoss.auth.module.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void duplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("이미 존재하는 회원입니다.");
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

    public void validateRefreshTokenOwner(String subject) {
        Long loginUserId = SecurityContextUtils.getUserId();
        if(!subject.equals(String.valueOf(loginUserId))) {
            throw new BadRequestException(ErrorCode.INVALID_REFRESH_TOKEN_SUBJECT);
        }
    }

    public User validateExistsBy(String email, String name, Phone phone) {
        return userRepository.findByEmailAndNameAndPhone(email, name, phone)
            .orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));
    }
}
