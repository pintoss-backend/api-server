package com.pintoss.auth.common.external.nice;

import com.pintoss.auth.common.external.nice.config.NiceApiProperties;
import com.pintoss.auth.common.external.nice.encryption.dto.EncryptedRequestDataCommand;
import com.pintoss.auth.common.external.nice.encryption.dto.GenerateHmacIntegrityCommand;
import com.pintoss.auth.common.external.nice.encryption.dto.GenerateSymmetricKeyCommand;
import com.pintoss.auth.common.external.nice.encryption.dto.SymmetricKeyResult;
import com.pintoss.auth.common.external.nice.encryption.NiceAuthHmacGenerator;
import com.pintoss.auth.common.external.nice.encryption.NiceAuthStandardAuthRequestEncryptor;
import com.pintoss.auth.common.external.nice.encryption.NiceAuthSymmetricKeyGenerator;
import com.pintoss.auth.common.external.nice.store.NiceApiCryptoToken;
import com.pintoss.auth.common.external.nice.provider.NiceApiTokenProvider;
import com.pintoss.auth.common.external.nice.util.NiceAuthCommandFactory;
import com.pintoss.auth.module.user.usecase.dto.NiceEncryptedDataResult;
import com.pintoss.auth.module.user.usecase.service.NiceAuthRequestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NiceAuthRequestClientImpl implements NiceAuthRequestClient {

    private final NiceApiProperties niceApiProperties;
    private final NiceApiTokenProvider niceApiTokenProvider;
    private final NiceAuthCommandFactory niceAuthCommandFactory;
    private final NiceAuthSymmetricKeyGenerator niceAuthSymmetricKeyGenerator;
    private final NiceAuthStandardAuthRequestEncryptor niceAuthStandardAuthRequestEncryptor;
    private final NiceAuthHmacGenerator niceAuthHmacGenerator;

    @Override
    public NiceEncryptedDataResult requestAuthData() {
        niceApiTokenProvider.getAccessToken();
        NiceApiCryptoToken cryptoToken = niceApiTokenProvider.getCryptoToken();

        GenerateSymmetricKeyCommand symmetricKeyCommand = niceAuthCommandFactory.createSymmetricKeyCommand(
            cryptoToken);
        SymmetricKeyResult symmetricKey = niceAuthSymmetricKeyGenerator.generate(symmetricKeyCommand);

        EncryptedRequestDataCommand encryptedRequestDataCommand = niceAuthCommandFactory.createEncryptedRequestDataCommand(
            symmetricKey, cryptoToken, niceApiProperties.getRedirectUri());

        String encData = niceAuthStandardAuthRequestEncryptor.encryption(
            encryptedRequestDataCommand);

        GenerateHmacIntegrityCommand hmacIntegrityCommand = niceAuthCommandFactory.createHmacIntegrityCommand(
            symmetricKey, encData);
        String integrity = niceAuthHmacGenerator.generate(hmacIntegrityCommand);

        return new NiceEncryptedDataResult(
            cryptoToken.getTokenVersionId(),
            encData,
            integrity
        );
    }

}
