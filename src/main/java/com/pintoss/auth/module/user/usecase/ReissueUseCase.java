package com.pintoss.auth.module.user.usecase;

import com.pintoss.auth.module.user.process.AuthTokenProvider;
import com.pintoss.auth.module.user.process.UserReader;
import com.pintoss.auth.module.user.process.UserValidator;
import com.pintoss.auth.module.user.process.domain.User;
import com.pintoss.auth.module.user.usecase.dto.ReissueCommand;
import com.pintoss.auth.module.user.usecase.dto.ReissueResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueUseCase {

    private final UserReader userReader;
    private final UserValidator userValidator;
    private final AuthTokenProvider authTokenProvider;

    public ReissueResult reissue(ReissueCommand command) {

        userValidator.verifyRefreshToken(command.getRefreshToken());

        String subject = authTokenProvider.extractSubject(command.getRefreshToken());

        User user = userReader.readById(Long.parseLong(subject));

        user.verifyRefreshToken(command.getRefreshToken());

        userValidator.verifyRefreshToken(user.getRefreshToken());

        String accessToken = authTokenProvider.generateToken(subject, false);
        String refreshToken = authTokenProvider.generateToken(subject, true);

        user.storeRefreshToken(refreshToken);

        return new ReissueResult(accessToken, refreshToken);
    }
}
