package com.pintoss.auth.common.client.billgate;

import com.galaxia.api.base64.BASE64Decoder;
import com.galaxia.api.crypto.Base64Encoder;
import com.galaxia.api.crypto.GalaxiaCipher;
import com.galaxia.api.crypto.Seed;
import com.galaxia.api.util.NumberUtil;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RefundApiClient {

    private final GalaxiaClient client;
    private final String KEY;
    private final String IV;

    public RefundApiClient(GalaxiaClient client, GalaxiaApiProperties properties) {
        this.client = client;
        this.KEY = properties.getSecret().getKey();
        this.IV = properties.getSecret().getIv();
    }

    public RefundResponse execute(String header, String body) {
        try {
            GalaxiaCipher cipher = new Seed();
            cipher.setKey(Base64.decode(KEY.getBytes("EUC-KR")));
            cipher.setIV(IV.getBytes("EUC-KR"));

            Base64Encoder encoder = new Base64Encoder();
            String encodedBody = header + encoder.encodeBuffer(cipher.encrypt(body.getBytes("EUC-KR")));

            byte[] payload = (NumberUtil.toZeroString(encodedBody.getBytes("EUC-KR").length, 4) + encodedBody).getBytes("EUC-KR");
            byte[] fullMessage = client.sendEncryptedRequest(payload);

            String response = new String(fullMessage, "EUC-KR");
            String plainHeader = response.substring(0, 98);
            String base64EncryptedBody = response.substring(98);
            BASE64Decoder decoder = new BASE64Decoder();

            byte[] encryptedBytes = decoder.decodeBuffer(base64EncryptedBody);

            // 복호화 후 불필요한 패딩 제거 (0x00 ~ 0x1F까지 제거)
            byte[] decryptedBytes = cipher.decrypt(encryptedBytes);

            int length = 0;
            for ( int i = 0 ; i< decryptedBytes.length; i++) {
                byte b = decryptedBytes[i];
                if( b == 0x000) break;
                length++;
            }

            byte[] cleanBytes = Arrays.copyOfRange(decryptedBytes, 0, length) ;
            String plainBody = new String(cleanBytes, StandardCharsets.UTF_8);

            System.out.println("[DEBUG] 복호화 결과 (plainBody) : " + plainBody);
            System.out.println("[DEBUG] 복호화 결과 (base64EncryptedBody): " + base64EncryptedBody);


            return parseCancelResponse(plainBody);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    private RefundResponse parseCancelResponse(String plain) {
        try {
            int idx = 0;
            return RefundResponse.builder()
                .responseCode(plain.substring(idx, idx += 4))
                .approvalCode(plain.substring(idx, idx += 32).trim())
                .openFlag(plain.substring(idx, idx += 1))
                .cardNo(plain.substring(idx, idx += 32).trim())
                .remainPrice(plain.substring(idx, idx += 8).trim())
                .itemName(plain.substring(idx, idx += 32).trim())
                .printFlag1(plain.substring(idx, idx += 1))
                .printMsg1(plain.substring(idx, idx += 32).trim())
                .printFlag2(plain.substring(idx, idx += 1))
                .printMsg2(plain.substring(idx, idx += 32).trim())
                .printFlag3(plain.substring(idx, idx += 1))
                .printMsg3(plain.substring(idx, idx += 32).trim())
                .printFlag4(plain.substring(idx, idx += 1))
                .printMsg4(plain.substring(idx, idx += 32).trim())
                .printFlag5(plain.substring(idx, idx += 1))
                .printMsg5(plain.substring(idx, idx += 32).trim())
                .build();
        } catch (Exception e) {
            log.error("[VoucherRefundParser] 응답 전문 파싱 실패: {}", e.getMessage(), e);
            throw new IllegalArgumentException("VoucherRefund 응답 파싱 실패");
        }
    }
}
