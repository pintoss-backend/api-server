package com.pintoss.auth.module.order.integration;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class VoucherPurchaseClient implements VoucherPurchaseService {

    private final TcpClient client;
    @Override
    public PurchaseResponse purchase(String orderId, String transactionId, String mid, Long taxAmount) {
        return PurchaseResponse.builder().cardNo(generateRandomString()).build();
//        try {
//            String requestHeader = PurchaseRequestBuilder.buildHeader(orderId);
//            String bodyPlain = PurchaseRequestBuilder.buildBody(orderId,transactionId, mid,taxAmount.toString());
//
//            GalaxiaCipher cipher = new Seed();
//            cipher.setKey(Base64.decode("Z2FsYXhpYW1vbmV5dHJlZQ==".getBytes("EUC-KR")));
//            cipher.setIV("1234567890123456".getBytes("EUC-KR"));
//
//            Base64Encoder encoder = new Base64Encoder();
//            String encodedBody = requestHeader + encoder.encodeBuffer(cipher.encrypt(bodyPlain.getBytes("EUC-KR")));
//
//            byte[] payload = (NumberUtil.toZeroString(encodedBody.getBytes("EUC-KR").length, 4) + encodedBody).getBytes("EUC-KR");
//            byte[] fullMessage = client.sendEncryptedRequest(payload);
//            String response = new String(fullMessage, StandardCharsets.UTF_8);
//            String plainHeader = response.substring(0,98);
//            String base64EncryptedBody = response.substring(98);
//            BASE64Decoder decoder = new BASE64Decoder();
//            byte[] encryptedBytes = decoder.decodeBuffer(base64EncryptedBody);
//
//            // 복호화 후 불필요한 패딩 제거 (0x00 ~ 0x1F까지 제거)
//            byte[] decryptedBytes = cipher.decrypt(encryptedBytes);
//
//            // 바이트에서 실제 문자열로 사용 가능한 부분만 추출 (EUC-KR 기준 null byte 제거)
//            int length = 0;
//            for (int i = 0; i < decryptedBytes.length; i++) {
//                byte b = decryptedBytes[i];
//                if (b == 0x00) break; // null byte 만나면 멈춤
//                length++;
//            }
//
//            byte[] cleanBytes = Arrays.copyOfRange(decryptedBytes, 0, length);
//            String plainBody = new String(cleanBytes, "EUC-KR");
//            System.out.println("[DEBUG] 복호화 결과:\n" + plainBody);
//            return parsePurchaseResponse(plainBody);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    private String generateRandomString() {
        Random random = new Random();
        int part1 = 10000 + random.nextInt(90000); // 10000 ~ 99999
        int part2 = 10000 + random.nextInt(90000);
        return part1 + "-" + part2;
    }
//
//    private PurchaseResponse parsePurchaseResponse(String plain) {
//        PurchaseResponse res = new PurchaseResponse();
//        int idx = 0;
//        res.setResponseCode(plain.substring(idx, idx += 4));
//        res.setAuthNo(plain.substring(idx, idx += 32).trim());
//        res.setOpenFlag(plain.substring(idx, idx += 1));
//        res.setCardNo(plain.substring(idx, idx += 32).trim());
//        res.setRemainPrice(plain.substring(idx, idx += 8).trim());
//        res.setItemName(plain.substring(idx, idx += 32).trim());
//        res.setPrintFlag1(plain.substring(idx, idx += 1));
//        res.setPrintMsg1(plain.substring(idx, idx += 32).trim());
//        res.setPrintFlag2(plain.substring(idx, idx += 1));
//        res.setPrintMsg2(plain.substring(idx, idx += 32).trim());
//        res.setPrintFlag3(plain.substring(idx, idx += 1));
//        res.setPrintMsg3(plain.substring(idx, idx += 32).trim());
//        res.setPrintFlag4(plain.substring(idx, idx += 1));
//        res.setPrintMsg4(plain.substring(idx, idx += 32).trim());
//        res.setPrintFlag5(plain.substring(idx, idx += 1));
//        res.setPrintMsg5(plain.substring(idx, idx += 32).trim());
//
//        log.info("[Galaxia 응답] 승인번호: {}, 카드번호: {}, 잔액: {}, 상품명: {}, 응답코드: {}",
//            res.getAuthNo(), res.getCardNo(), res.getRemainPrice(), res.getItemName(), res.getResponseCode());
//
//        return res;
//    }
}
