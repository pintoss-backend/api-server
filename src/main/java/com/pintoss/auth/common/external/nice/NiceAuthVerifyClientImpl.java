package com.pintoss.auth.common.external.nice;

import com.pintoss.auth.common.external.nice.decipher.NiceAuthAuthenticationHandler;
import com.pintoss.auth.common.external.nice.decipher.NiceAuthAuthenticationResponseDecipher;
import com.pintoss.auth.common.external.nice.decipher.dto.AuthenticationDataDecipherCommand;
import com.pintoss.auth.common.external.nice.decipher.dto.AuthenticationDataDecipherResult;
import com.pintoss.auth.common.external.nice.provider.NiceApiTokenProvider;
import com.pintoss.auth.common.external.nice.store.NiceApiSymmetricKey;
import com.pintoss.auth.common.external.nice.util.NiceAuthCommandFactory;
import com.pintoss.auth.module.user.usecase.dto.NiceVerificationResult;
import com.pintoss.auth.module.user.usecase.service.NiceAuthVerifyClient;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NiceAuthVerifyClientImpl implements NiceAuthVerifyClient {

    private final NiceAuthCommandFactory niceAuthCommandFactory;
    private final NiceApiTokenProvider niceApiTokenRepository;
    private final NiceAuthAuthenticationResponseDecipher niceAuthAuthenticationResponseDecipher;
    private final NiceAuthAuthenticationHandler niceAuthAuthenticationHandler;

    @Override
    public NiceVerificationResult verify(String tokenVersionId, String encData, String integrityValue) {
        NiceApiSymmetricKey symmetricKey = niceApiTokenRepository.getSymmetricKey();

        AuthenticationDataDecipherCommand authenticationDataEncryptor = niceAuthCommandFactory.createAuthenticationDataEncryptor(
            symmetricKey.getKey(), symmetricKey.getIv(),
            encData);

        AuthenticationDataDecipherResult decipher = niceAuthAuthenticationResponseDecipher.decipher(authenticationDataEncryptor);
        if( niceAuthAuthenticationHandler.handler(decipher) ) {
            return new NiceVerificationResult(false, null, null);
        }
        return new NiceVerificationResult(true, URLDecoder.decode(decipher.getUtf8_name(), StandardCharsets.UTF_8), decipher.getMobileno());
    }
}
