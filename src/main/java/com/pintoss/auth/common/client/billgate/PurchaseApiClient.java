package com.pintoss.auth.common.client.billgate;

import com.galaxia.api.base64.BASE64Decoder;
import com.galaxia.api.crypto.Base64Encoder;
import com.galaxia.api.crypto.GalaxiaCipher;
import com.galaxia.api.crypto.Seed;
import com.galaxia.api.util.NumberUtil;
import com.pintoss.auth.module.order.integration.PurchaseRequestBuilder;
import com.pintoss.auth.module.order.integration.PurchaseResponse;
import com.pintoss.auth.module.payment.application.PaymentMethodType;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PurchaseApiClient {

    private final GalaxiaClient client;
    private final String KEY;
    private final String IV;

    public PurchaseApiClient(GalaxiaClient client, GalaxiaApiProperties properties) {
        this.client = client;
        this.KEY = properties.getSecret().getKey();
        this.IV = properties.getSecret().getIv();
    }

    public PurchaseResponse purchase(String orderNo, String transactionId, String mid, Long amount, PaymentMethodType paymentMethodType, Long salePrice, String productCode) {
//        return PurchaseResponse.builder().cardNo(generateRandomString()).build();
        try {
            String requestHeader = PurchaseRequestBuilder.buildHeader(orderNo);
            String bodyPlain = PurchaseRequestBuilder.buildBody(orderNo,transactionId, mid,salePrice.toString(), paymentMethodType, salePrice.toString(), productCode);

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
            return parsePurchaseResponse(cleanBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private PurchaseResponse parsePurchaseResponse(byte[] plainBytes) throws UnsupportedEncodingException {
        PurchaseResponse res = new PurchaseResponse();
        int idx = 0;

        res.setResponseCode(readField(plainBytes, idx, 4)); idx += 4;
        res.setSuccess("0000".equals(res.getResponseCode()));
        res.setApprovalCode(readField(plainBytes, idx, 32)); idx += 32;
        res.setOpenFlag(readField(plainBytes, idx, 1)); idx += 1;
        res.setCardNo(readField(plainBytes, idx, 32)); idx += 32;
        res.setRemainPrice(readField(plainBytes, idx, 8)); idx += 8;
        res.setItemName(readField(plainBytes, idx, 32)); idx += 32;

        res.setPrintFlag1(readField(plainBytes, idx, 1)); idx += 1;
        res.setPrintMsg1(readField(plainBytes, idx, 32)); idx += 32;
        if(res.getItemName().equals("도서문화상품권")) {
            String msg = res.getPrintMsg1();
            Matcher matcher = Pattern.compile("비밀번호[:：\\s]*([0-9]{4})").matcher(msg);
            if(matcher.find()) {
                String password = matcher.group();
                res.setCardNo(res.getCardNo()+"-"+password);
            }
        }
        res.setPrintFlag2(readField(plainBytes, idx, 1)); idx += 1;
        res.setPrintMsg2(readField(plainBytes, idx, 32)); idx += 32;

        res.setPrintFlag3(readField(plainBytes, idx, 1)); idx += 1;
        res.setPrintMsg3(readField(plainBytes, idx, 32)); idx += 32;

        res.setPrintFlag4(readField(plainBytes, idx, 1)); idx += 1;
        res.setPrintMsg4(readField(plainBytes, idx, 32)); idx += 32;

        res.setPrintFlag5(readField(plainBytes, idx, 1)); idx += 1;
        res.setPrintMsg5(readField(plainBytes, idx, 32)); idx += 32;

        log.debug("[Galaxia 응답] 승인번호: {}, 카드번호: {}, 잔액: {}, 상품명: {}, 응답코드: {}",
            res.getApprovalCode(), res.getCardNo(), res.getRemainPrice(), res.getItemName(), res.getResponseCode());

        return res;
    }

    private String readField(byte[] bytes, int offset, int length) throws UnsupportedEncodingException {
        return new String(Arrays.copyOfRange(bytes, offset, offset + length), "EUC-KR").trim();
    }
}
