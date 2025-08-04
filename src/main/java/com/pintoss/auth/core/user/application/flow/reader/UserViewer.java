package com.pintoss.auth.core.user.application.flow.reader;

import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.support.exception.BadRequestException;
import com.pintoss.auth.core.user.application.repository.UserRepository;
import com.pintoss.auth.core.user.domain.User;
import com.pintoss.auth.core.user.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserViewer {

    private final UserRepository userRepository;

    public UserInfo getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BadRequestException(
            ErrorCode.USER_NOT_FOUND));
        return UserInfo.builder()
            .email(user.getEmail())
            .name(user.getName())
            .phone(user.getPhone().getValue())
            .build();
    }
}
