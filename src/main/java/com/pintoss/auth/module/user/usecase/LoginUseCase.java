package com.pintoss.auth.module.user.usecase;

import com.pintoss.auth.module.user.process.AuthTokenProvider;
import com.pintoss.auth.module.user.process.UserReader;
import com.pintoss.auth.module.user.process.UserValidator;
import com.pintoss.auth.module.user.process.domain.User;
import com.pintoss.auth.module.user.usecase.dto.LoginCommand;
import com.pintoss.auth.module.user.usecase.dto.LoginResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserReader userReader;
    private final UserValidator userValidator;
    private final AuthTokenProvider authTokenProvider;

    @Transactional
    public LoginResult login(LoginCommand command) {
        // 1. 아이디 확인
        User user = userReader.readByEmail(command.getEmail());

        // 2. 비밀번호 확인
        userValidator.matchPassword(command.getPassword(), user.getPassword());

        // 3. ACCESS, REFRESH 토큰 발급 및 저장
        String accessToken = authTokenProvider.generateToken(String.valueOf(user.getId()),false);
        String refreshToken = authTokenProvider.generateToken(String.valueOf(user.getId()),true);
        user.storeRefreshToken(refreshToken);

        return new LoginResult(accessToken, refreshToken);
    }
}
