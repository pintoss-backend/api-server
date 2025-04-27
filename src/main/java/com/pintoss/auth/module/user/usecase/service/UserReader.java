package com.pintoss.auth.module.user.usecase.service;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.module.user.model.Phone;
import com.pintoss.auth.module.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    public User readByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));
    }

    public User readById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));
    }

    public User readByNameAndPhone(String name, Phone phone) {
        return userRepository.findByNameAndPhone(name, phone)
            .orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));
    }
}
