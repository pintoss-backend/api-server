package com.pintoss.auth.core.user.application;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.api.security.SecurityContextUtils;
import com.pintoss.auth.core.user.domain.User;
import com.pintoss.auth.core.user.application.flow.reader.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PasswordUpdateUseCase {

    private final UserReader userReader;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void updatePassword(String originPassword, String newPassword) {

        Long userId = SecurityContextUtils.getUserId();
        User user = userReader.readById(userId);

        // 평문 originPassword와 저장된 암호화된 비밀번호 비교
        if (!passwordEncoder.matches(originPassword, user.getPassword())) {
            throw new BadRequestException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        // 새 비밀번호 암호화 후 저장
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.updatePassword(encodedNewPassword);
    }
}
