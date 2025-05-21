package com.pintoss.auth.common.client.nice.decipher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pintoss.auth.common.client.nice.decipher.dto.AuthenticationDataDecipherCommand;
import com.pintoss.auth.common.client.nice.decipher.dto.AuthenticationDataDecipherResult;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class NiceAuthAuthenticationResponseDecipher {
    public AuthenticationDataDecipherResult decipher(
        AuthenticationDataDecipherCommand command) {
        AuthenticationDataDecipherResult authenticationDataDecipherResult;
        try{
            SecretKey secureKey = new SecretKeySpec(command.getKey().getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(command.getIv().getBytes()));

            byte[] decodedBytes = Base64.getDecoder().decode(command.getEncData());
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            String json = new String(decryptedBytes, StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            authenticationDataDecipherResult = objectMapper.readValue(json, AuthenticationDataDecipherResult.class);
            return authenticationDataDecipherResult;
        } catch (Exception e) {
            return null;
        }
    }
}
