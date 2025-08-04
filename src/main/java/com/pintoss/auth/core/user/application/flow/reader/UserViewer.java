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

    /**
     * Retrieves user information for the specified user ID.
     *
     * If the user does not exist, throws a {@code BadRequestException} with the {@code USER_NOT_FOUND} error code.
     *
     * @param userId the unique identifier of the user to retrieve
     * @return a {@code UserInfo} object containing the user's email, name, and phone number
     * @throws BadRequestException if no user is found with the given ID
     */
    public UserInfo getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BadRequestException(
            ErrorCode.USER_NOT_FOUND));
        return UserInfo.builder()
            .email(user.getEmail())
            .name(user.getName())
            .phone(user.getPhone().getPhone())
            .build();
    }
}
