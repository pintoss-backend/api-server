package com.pintoss.auth.client.nice.encryption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.support.exception.client.BadRequestException;
import com.pintoss.auth.client.nice.encryption.dto.NiceAuthStandardAuthRequest;
import com.pintoss.auth.client.nice.encryption.dto.EncryptedRequestDataCommand;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class NiceAuthStandardAuthRequestEncryptor {

    // TODO : InternalException으로 변경하기
    public String encryption(EncryptedRequestDataCommand command) {
        SecretKey secureKey = new SecretKeySpec(command.getKey().getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            throw new BadRequestException(ErrorCode.UNSUPPORTED_ENCRYPTION_ALGORITHM, "요청 데이터 암호화 중 문제가 발생했습니다.");
        } catch (NoSuchPaddingException e) {
            throw new BadRequestException(ErrorCode.UNSUPPORTED_PADDING_SCHEMA, "요청 데이터 암호화 중 문제가 발생했습니다.");
        }
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(command.getIv().getBytes(StandardCharsets.UTF_8)));
        } catch (InvalidKeyException e) {
            throw new BadRequestException(ErrorCode.INVALID_ENCRYPTION_KEY, "요청 데이터 암호화 중 문제가 발생했습니다.");
        } catch (InvalidAlgorithmParameterException e) {
            throw new BadRequestException(ErrorCode.INVALID_ALGORITHM_PARAMETER, "요청 데이터 암호화 중 문제가 발생했습니다.");
        }
        ObjectMapper objectMapper = new ObjectMapper();

        NiceAuthStandardAuthRequest reqData = new NiceAuthStandardAuthRequest(
                command.getReqNo().trim(),
                command.getRedirectUrl(),
                command.getSiteCode(),
                command.getPurpose()
        );
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(reqData);
        } catch (JsonProcessingException e) {
            throw new BadRequestException(ErrorCode.JSON_SERIALIZATION, "NICE API 응답 값을 JSON 형태로 파싱할 수 없습니다.");
        }
        byte[] encrypted = null;
        try {
            encrypted = cipher.doFinal(jsonString.getBytes());
        } catch (IllegalBlockSizeException e) {
            throw new BadRequestException(ErrorCode.ILLEGAL_BLOCK_SIZE);
        } catch (BadPaddingException e) {
            throw new BadRequestException(ErrorCode.BAD_PADDING);
        }
        return Base64.getEncoder().encodeToString(encrypted);
    }
}
