package com.pintoss.auth.common.external.nice.encryption;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.common.external.nice.store.NiceApiSymmetricKey;
import com.pintoss.auth.common.external.nice.provider.NiceApiTokenProvider;
import com.pintoss.auth.common.external.nice.encryption.dto.GenerateSymmetricKeyCommand;
import com.pintoss.auth.common.external.nice.encryption.dto.SymmetricKeyResult;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class NiceAuthSymmetricKeyGenerator {

    private final NiceApiTokenProvider niceApiTokenProvider;

    public NiceAuthSymmetricKeyGenerator(NiceApiTokenProvider niceApiTokenProvider) {
        this.niceApiTokenProvider = niceApiTokenProvider;
    }

    public SymmetricKeyResult generate(GenerateSymmetricKeyCommand command) {
        String symmetricKey = command.getReqDtim().trim() + command.getReqNo() + command.getTokenValue();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new BadRequestException(ErrorCode.UNSUPPORTED_ENCRYPTION_ALGORITHM);
        }
        md.update(symmetricKey.getBytes());
        byte[] arrHashValue = md.digest();
        String base64Encoded = Base64.getEncoder().encodeToString(arrHashValue);

        String key = base64Encoded.substring(0, 16);
        String iv = base64Encoded.substring(base64Encoded.length() - 16);
        String hmacKey = base64Encoded.substring(0, 32);

        NiceApiSymmetricKey symmetricKeyObj = new NiceApiSymmetricKey(key, iv);
        niceApiTokenProvider.saveSymmetricKey(symmetricKeyObj);

        return new SymmetricKeyResult(key, iv, hmacKey);
    }
}
