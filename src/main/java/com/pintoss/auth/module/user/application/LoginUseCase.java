package com.pintoss.auth.module.user.application;

import com.pintoss.auth.module.user.core.AuthTokenService;
import com.pintoss.auth.module.user.core.UserReader;
import com.pintoss.auth.module.user.core.UserValidator;
import com.pintoss.auth.module.user.domain.User;
import com.pintoss.auth.module.user.application.dto.LoginCommand;
import com.pintoss.auth.module.user.application.dto.LoginResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserReader userReader;
    private final UserValidator userValidator;
    private final AuthTokenService authTokenService;

    @Transactional
    public LoginResult login(LoginCommand command) {
        // 1. 아이디 확인
        User user = userReader.readByEmail(command.getEmail());

        // 2. 비밀번호 확인
        userValidator.matchPassword(command.getPassword(), user.getPassword());

        // 3. ACCESS, REFRESH 토큰 발급 및 저장
        String accessToken = authTokenService.generateToken(user,false);
        String refreshToken = authTokenService.generateToken(user,true);
        user.storeRefreshToken(refreshToken);

        return new LoginResult(accessToken, refreshToken);
    }
}
