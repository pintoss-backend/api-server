package com.pintoss.auth.module.user.application;

import com.pintoss.auth.module.user.core.AuthTokenService;
import com.pintoss.auth.module.user.core.UserReader;
import com.pintoss.auth.module.user.core.UserValidator;
import com.pintoss.auth.module.user.domain.User;
import com.pintoss.auth.module.user.application.dto.ReissueCommand;
import com.pintoss.auth.module.user.application.dto.ReissueResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueUseCase {

    private final UserReader userReader;
    private final UserValidator userValidator;
    private final AuthTokenService authTokenService;

    public ReissueResult reissue(ReissueCommand command) {
        // 1. 리프레쉬 토큰에서 subject (userId)추출
        String subject = authTokenService.extractSubject(command.getRefreshToken());

        // 2. 요청 리프레쉬 토큰의 subject와 액세스 토큰의 인증정보의 userId와 동일한지 검증
        userValidator.validateRefreshTokenOwner(subject);

        // 3. 유저 조회
        User user = userReader.readById(Long.parseLong(subject));

        // 4. 새로운 리프레쉬 토큰 발급
        String accessToken = authTokenService.generateToken(user, false);
        String refreshToken = authTokenService.generateToken(user, true);

        user.storeRefreshToken(refreshToken);

        return new ReissueResult(accessToken, refreshToken);
    }
}
