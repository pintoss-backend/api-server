package com.pintoss.auth.common.client.nice.util;

import com.pintoss.auth.common.client.nice.store.NiceApiCryptoToken;
import com.pintoss.auth.common.client.nice.decipher.dto.AuthenticationDataDecipherCommand;
import com.pintoss.auth.common.client.nice.encryption.dto.EncryptedRequestDataCommand;
import com.pintoss.auth.common.client.nice.encryption.dto.GenerateHmacIntegrityCommand;
import com.pintoss.auth.common.client.nice.encryption.dto.GenerateSymmetricKeyCommand;
import com.pintoss.auth.common.client.nice.encryption.dto.SymmetricKeyResult;
import org.springframework.stereotype.Component;

@Component
public class NiceAuthCommandFactory {
    public GenerateSymmetricKeyCommand createSymmetricKeyCommand(NiceApiCryptoToken cryptoToken) {
        return GenerateSymmetricKeyCommand.create(
                cryptoToken.getReqDtim(),
                cryptoToken.getReqNo(),
                cryptoToken.getTokenValue()
        );
    }

    public EncryptedRequestDataCommand createEncryptedRequestDataCommand(SymmetricKeyResult
        symmetricKey, NiceApiCryptoToken cryptoToken, String redirectUri, String purpose) {
        return EncryptedRequestDataCommand.create(
                symmetricKey.getKey(),
                symmetricKey.getIv(),
                cryptoToken.getReqNo(),
                redirectUri,
                cryptoToken.getSiteCode(),
                purpose
        );
    }

    public GenerateHmacIntegrityCommand createHmacIntegrityCommand(SymmetricKeyResult symmetricKey, String encData) {
        return GenerateHmacIntegrityCommand.create(
                symmetricKey.getHmacKey(),
                encData
        );
    }

    public AuthenticationDataDecipherCommand createAuthenticationDataEncryptor(String key, String iv, String encData) {
        return AuthenticationDataDecipherCommand.create(key, iv, encData);
    }
}
