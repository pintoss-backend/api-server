package com.pintoss.auth.common.client.billgate;

import com.galaxia.api.base64.BASE64Decoder;
import com.galaxia.api.crypto.Base64Encoder;
import com.galaxia.api.crypto.GalaxiaCipher;
import com.galaxia.api.crypto.Seed;
import com.galaxia.api.util.NumberUtil;
import com.pintoss.auth.module.order.integration.PurchaseResult;
import com.pintoss.auth.module.payment.application.PaymentMethodType;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PurchaseApiClient {

    private static final String ENCODING = "EUC-KR";
    private final GalaxiaClient client;
    private final String KEY;
    private final String IV;

    public PurchaseApiClient(GalaxiaClient client, GalaxiaApiProperties properties) {
        this.client = client;
        this.KEY = properties.getSecret().getKey();
        this.IV = properties.getSecret().getIv();
    }

//    public PurchaseResult execute(String orderNo, String transId, String mid, PaymentMethodType paymentMethodType, String productCode) {
//        try {
//            String request = buildEncryptedRequest(orderNo, transId, mid, paymentMethodType, productCode);
//        } catch (Exception e) {
//            log.error("갤럭시아머니트리 구매 요청 생성 중 오류 발생: {}", e.getMessage(), e);
//            return null;
//        }
//    }
//
//
//    private String buildEncryptedRequest(String orderNo, String transactionId, String mid, PaymentMethodType paymentMethodType, String productCode) throws Exception {
//        String header = PurchaseRequestBuilder.buildHeader(orderNo);
//        String body = PurchaseRequestBuilder.buildBody(orderNo, transactionId, mid, salePrice.toString(), paymentMethodType, salePrice.toString(), productCode);
//
//        GalaxiaCipher cipher = new Seed();
//        cipher.setKey(Base64.decode(KEY.getBytes(ENCODING)));
//        cipher.setIV(IV.getBytes(ENCODING));
//
//        byte[] encryptedBody = cipher.encrypt(body.getBytes(ENCODING));
//
//        Base64Encoder encoder = new Base64Encoder();
//        return header + encoder.encodeBuffer(encryptedBody);
//    }

    public PurchaseResult purchase(String orderNo, String transactionId, String mid, Long amount, PaymentMethodType paymentMethodType, Long salePrice, String productCode) {
        try {
            String requestHeader = PurchaseRequestBuilder.buildHeader(orderNo);
            String bodyPlain = PurchaseRequestBuilder.buildBody(orderNo,transactionId, mid,salePrice.toString(), paymentMethodType, salePrice.toString(), productCode);

            GalaxiaCipher cipher = new Seed();
            cipher.setKey(Base64.decode(KEY.getBytes(ENCODING)));
            cipher.setIV(IV.getBytes(ENCODING));

            Base64Encoder encoder = new Base64Encoder();
            String encodedBody = requestHeader + encoder.encodeBuffer(cipher.encrypt(bodyPlain.getBytes("EUC-KR")));

            byte[] payload = (NumberUtil.toZeroString(encodedBody.getBytes(ENCODING).length, 4) + encodedBody).getBytes(ENCODING);
            byte[] fullMessage = client.sendEncryptedRequest(payload);
            String response = new String(fullMessage, ENCODING);
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
            String plainBody = new String(cleanBytes, ENCODING);
            log.info("[DEBUG] 복호화 결과 (plainBody): " + plainBody);
            log.debug("[DEBUG] 복호화 결과(base64EncryptedBody): " + base64EncryptedBody);
            return parsePurchaseResponse(plainBody);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private PurchaseResult parsePurchaseResponse(String plain) {
        PurchaseResult res = new PurchaseResult();
        int idx = 0;
        res.setResponseCode(plain.substring(idx, idx += 4));
        res.setSuccess(res.getResponseCode().equals("0000")? true : false);
        res.setApprovalCode(plain.substring(idx, idx += 32).trim());
        res.setOpenFlag(plain.substring(idx, idx += 1));
        res.setCardNo(plain.substring(idx, idx += 32).trim());
        res.setRemainPrice(plain.substring(idx, idx += 8).trim());
        res.setItemName(plain.substring(idx, idx += 32).trim());
        res.setPrintFlag1(plain.substring(idx, idx += 1));
        res.setPrintMsg1(plain.substring(idx, idx += 32).trim());
        res.setPrintFlag2(plain.substring(idx, idx += 1));
        res.setPrintMsg2(plain.substring(idx, idx += 32).trim());
        res.setPrintFlag3(plain.substring(idx, idx += 1));
        res.setPrintMsg3(plain.substring(idx, idx += 32).trim());
        res.setPrintFlag4(plain.substring(idx, idx += 1));
        res.setPrintMsg4(plain.substring(idx, idx += 32).trim());
        res.setPrintFlag5(plain.substring(idx, idx += 1));
        res.setPrintMsg5(plain.substring(idx, idx += 32).trim());

        log.debug("[Galaxia 응답] 승인번호: {}, 카드번호: {}, 잔액: {}, 상품명: {}, 응답코드: {}",
            res.getApprovalCode(), res.getCardNo(), res.getRemainPrice(), res.getItemName(), res.getResponseCode());

        return res;
    }
}
