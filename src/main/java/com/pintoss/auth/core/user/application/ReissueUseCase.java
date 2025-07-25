package com.pintoss.auth.core.user.application;

import com.pintoss.auth.api.support.security.SecurityContextUtils;
import com.pintoss.auth.core.support.cache.CacheManagerWrapper;
import com.pintoss.auth.core.support.cache.CacheType;
import com.pintoss.auth.core.support.exception.CoreErrorCode;
import com.pintoss.auth.core.support.exception.CoreException;
import com.pintoss.auth.core.user.application.dto.ReissueCommand;
import com.pintoss.auth.core.user.application.dto.ReissueResult;
import com.pintoss.auth.core.user.application.flow.processor.AuthTokenProcessor;
import com.pintoss.auth.core.user.application.flow.reader.UserReader;
import com.pintoss.auth.core.user.application.flow.validator.UserValidator;
import com.pintoss.auth.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueUseCase {

    private final UserReader userReader;
    private final UserValidator userValidator;
    private final AuthTokenProcessor authTokenProcessor;
    private final CacheManagerWrapper cacheManagerWrapper;

    public ReissueResult reissue(ReissueCommand command) {
        // 1. 리프레쉬 토큰에서 subject (userId)추출
        String subject = authTokenProcessor.extractSubject(command.getRefreshToken());

        // 2. 요청 리프레쉬 토큰의 subject와 액세스 accessTokenSubject 인증정보의 userId와 동일한지 검증
        userValidator.validateRefreshTokenOwner(subject, SecurityContextUtils.getUserId());

        // 3. 유저 조회
        User user = userReader.readById(Long.parseLong(subject));

        // 4. 캐시에서 리프레쉬 토큰 조회
        if (!cacheManagerWrapper.exists(CacheType.AUTH_TOKEN_CACHE.getCacheName(), user.getId())) {
            throw new CoreException(CoreErrorCode.SESSION_EXPIRED);
        }

        // 5. 새로운 리프레쉬 토큰 발급
        String accessToken = authTokenProcessor.generateToken(user, false);
        String refreshToken = authTokenProcessor.generateToken(user, true);

        user.storeRefreshToken(refreshToken);

        return new ReissueResult(accessToken, refreshToken);
    }
}
