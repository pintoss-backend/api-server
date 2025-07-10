package com.pintoss.auth.core.user.application;

import com.pintoss.auth.core.user.application.flow.processor.AuthTokenProcessor;
import com.pintoss.auth.core.user.application.flow.reader.UserReader;
import com.pintoss.auth.core.user.application.flow.validator.UserValidator;
import com.pintoss.auth.core.user.domain.User;
import com.pintoss.auth.core.user.application.dto.ReissueCommand;
import com.pintoss.auth.core.user.application.dto.ReissueResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueUseCase {

    private final UserReader userReader;
    private final UserValidator userValidator;
    private final AuthTokenProcessor authTokenProcessor;

    public ReissueResult reissue(ReissueCommand command) {
        // 1. 리프레쉬 토큰에서 subject (userId)추출
        String subject = authTokenProcessor.extractSubject(command.getRefreshToken());

        // 2. 요청 리프레쉬 토큰의 subject와 액세스 토큰의 인증정보의 userId와 동일한지 검증
        userValidator.validateRefreshTokenOwner(subject);

        // 3. 유저 조회
        User user = userReader.readById(Long.parseLong(subject));

        // 4. 새로운 리프레쉬 토큰 발급
        String accessToken = authTokenProcessor.generateToken(user, false);
        String refreshToken = authTokenProcessor.generateToken(user, true);

        user.storeRefreshToken(refreshToken);

        return new ReissueResult(accessToken, refreshToken);
    }
}
