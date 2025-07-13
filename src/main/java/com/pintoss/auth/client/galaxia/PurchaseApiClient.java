package com.pintoss.auth.client.galaxia;

import com.galaxia.api.base64.BASE64Decoder;
import com.galaxia.api.crypto.Base64Encoder;
import com.galaxia.api.crypto.GalaxiaCipher;
import com.galaxia.api.crypto.Seed;
import com.galaxia.api.util.NumberUtil;
import com.pintoss.auth.core.order.domain.PurchaseResult;
import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import com.pintoss.auth.core.voucher.application.flow.external.Purchaser;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PurchaseApiClient implements Purchaser {

    private final GalaxiaClient client;
    private final String KEY;
    private final String IV;

    public PurchaseApiClient(GalaxiaClient client, GalaxiaApiProperties properties) {
        this.client = client;
        this.KEY = properties.getSecret().getKey();
        this.IV = properties.getSecret().getIv();
    }

    @Override
    public PurchaseResult purchase(String orderNo, String transactionId, String mid, Long paymentPrice, PaymentMethodType paymentMethodType, Long salePrice, String productCode) {
        try {
            String requestHeader = PurchaseRequestBuilder.buildHeader(orderNo);
            String bodyPlain = PurchaseRequestBuilder.buildBody(orderNo, transactionId, mid, paymentPrice.toString(), paymentMethodType, salePrice.toString(), productCode);

            GalaxiaCipher cipher = new Seed();
            cipher.setKey(Base64.decode(KEY.getBytes("EUC-KR")));
            cipher.setIV(IV.getBytes("EUC-KR"));

            Base64Encoder encoder = new Base64Encoder();
            String encodedBody = requestHeader + encoder.encodeBuffer(cipher.encrypt(bodyPlain.getBytes("EUC-KR")));

            byte[] payload = (NumberUtil.toZeroString(encodedBody.getBytes("EUC-KR").length, 4) + encodedBody).getBytes("EUC-KR");
            byte[] fullMessage = client.sendEncryptedRequest(payload);
            String response = new String(fullMessage, "EUC-KR");
            String plainHeader = response.substring(0,98);
            String base64EncryptedBody = response.substring(98);
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] encryptedBytes = decoder.decodeBuffer(base64EncryptedBody);

            // 복호화 후 불필요한 패딩 제거 (0x00 ~ 0x1F까지 제거)
            byte[] decryptedBytes = cipher.decrypt(encryptedBytes);

            // 바이트에서 실제 문자열로 사용 가능한 부분만 추출 (EUC-KR 기준 null byte 제거)
            int length = 0;
            for (int i = 0; i < decryptedBytes.length; i++) {
                byte b = decryptedBytes[i];
                if (b == 0x00) break; // null byte 만나면 멈춤
                length++;
            }

            byte[] cleanBytes = Arrays.copyOfRange(decryptedBytes, 0, length);
            String plainBody = new String(cleanBytes, "EUC-KR");
            log.info("[DEBUG] 복호화 결과 (plainBody): " + plainBody);
            return GalaxiaPurchaseResponse.fromBytes(cleanBytes).toResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
