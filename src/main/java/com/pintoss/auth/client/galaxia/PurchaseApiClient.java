package com.pintoss.auth.client.galaxia;

import com.galaxia.api.base64.BASE64Decoder;
import com.galaxia.api.crypto.Base64Encoder;
import com.galaxia.api.crypto.GalaxiaCipher;
import com.galaxia.api.crypto.Seed;
import com.galaxia.api.util.NumberUtil;
import com.pintoss.auth.core.voucher.application.dto.PurchaseResult;
import com.pintoss.auth.core.voucher.application.dto.VoucherPurchaseCommand;
import com.pintoss.auth.core.voucher.application.flow.external.Purchaser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class PurchaseApiClient implements Purchaser {

    private static final String EUC_ENCODING = "EUC-KR";

    private final GalaxiaClient client;
    private final String KEY;
    private final String IV;

    public PurchaseApiClient(GalaxiaClient client, GalaxiaApiProperties properties) {
        this.client = client;
        this.KEY = properties.getSecret().getKey();
        this.IV = properties.getSecret().getIv();
    }

    @Override
    public PurchaseResult purchase(VoucherPurchaseCommand command) {
        try {
            String requestHeader = PurchaseRequestBuilder.buildHeader(command.getOrderNo());
            String bodyPlain = PurchaseRequestBuilder.buildBody(command);

            GalaxiaCipher cipher = getGalaxiaCipher();

            Base64Encoder encoder = new Base64Encoder();
            String encodedBody = requestHeader + encoder.encodeBuffer(cipher.encrypt(bodyPlain.getBytes(EUC_ENCODING)));

            byte[] payload = (NumberUtil.toZeroString(encodedBody.getBytes(EUC_ENCODING).length, 4) + encodedBody).getBytes(EUC_ENCODING);
            byte[] fullMessage = client.sendEncryptedRequest(payload);
            String response = new String(fullMessage, EUC_ENCODING);

            String plainHeader = response.substring(0,98);
            String base64EncryptedBody = response.substring(98);
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] encryptedBytes = decoder.decodeBuffer(base64EncryptedBody);

            // 복호화 후 불필요한 패딩 제거 (0x00 ~ 0x1F까지 제거)
            byte[] decryptedBytes = cipher.decrypt(encryptedBytes);

            // 바이트에서 실제 문자열로 사용 가능한 부분만 추출 (EUC-KR 기준 null byte 제거)
            int length = 0;
            while (length < decryptedBytes.length && decryptedBytes[length] != 0x00) {
                length++;
            }

            byte[] cleanBytes = Arrays.copyOfRange(decryptedBytes, 0, length);
            String plainBody = new String(cleanBytes, EUC_ENCODING);
            log.info("[DEBUG] 복호화 결과 (plainBody): " + plainBody);

            return GalaxiaPurchaseResponse.fromBytes(cleanBytes).toResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private GalaxiaCipher getGalaxiaCipher() throws Exception {
        GalaxiaCipher cipher = new Seed();
        cipher.setKey(Base64.decode(KEY.getBytes(EUC_ENCODING)));
        cipher.setIV(IV.getBytes(EUC_ENCODING));
        return cipher;
    }
}
