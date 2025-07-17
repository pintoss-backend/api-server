package com.pintoss.auth.client.nice.encryption;

import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.support.exception.client.BadRequestException;
import com.pintoss.auth.client.nice.encryption.dto.GenerateHmacIntegrityCommand;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class NiceAuthHmacGenerator {

    // TODO : InternalException으로 변경하기
    public String generate(GenerateHmacIntegrityCommand command){
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new BadRequestException(ErrorCode.UNSUPPORTED_ENCRYPTION_ALGORITHM, "HMAC 무결성 체크 값 생성 중 문제가 발생했습니다.");
        }
        SecretKeySpec keySpec = new SecretKeySpec(command.getHmacKey().getBytes(), "HmacSHA256");
        try {
            mac.init(keySpec);
        } catch (InvalidKeyException e) {
            throw new BadRequestException(ErrorCode.INVALID_HMAC_KEY);
        }
        byte[] hmac256 = mac.doFinal(command.getEncData().getBytes());
        return Base64.getEncoder().encodeToString(hmac256);
    }

}
