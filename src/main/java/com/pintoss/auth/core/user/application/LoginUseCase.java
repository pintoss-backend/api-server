package com.pintoss.auth.core.user.application;

import com.pintoss.auth.core.user.application.flow.processor.AuthTokenProcessor;
import com.pintoss.auth.core.user.application.flow.reader.UserReader;
import com.pintoss.auth.core.user.application.flow.validator.UserValidator;
import com.pintoss.auth.storage.user.jpa.entity.User;
import com.pintoss.auth.core.user.application.dto.LoginCommand;
import com.pintoss.auth.core.user.application.dto.LoginResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserReader userReader;
    private final UserValidator userValidator;
    private final AuthTokenProcessor authTokenProcessor;

    @Transactional
    public LoginResult login(LoginCommand command) {
        // 1. 아이디 확인
        User user = userReader.readByEmail(command.getEmail());

        // 2. 비밀번호 확인
        userValidator.matchPassword(command.getPassword(), user.getPassword());

        // 3. ACCESS, REFRESH 토큰 발급 및 저장
        String accessToken = authTokenProcessor.generateToken(user,false);
        String refreshToken = authTokenProcessor.generateToken(user,true);
        user.storeRefreshToken(refreshToken);

        return new LoginResult(accessToken, refreshToken);
    }
}
