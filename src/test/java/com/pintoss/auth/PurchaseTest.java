//package com.pintoss.auth;
//
//import com.galaxia.api.base64.BASE64Decoder;
//import com.galaxia.api.crypto.GalaxiaCipher;
//import com.galaxia.api.crypto.Seed;
//import com.pintoss.auth.common.client.billgate.GalaxiaPurchaseResponse;
//import java.util.Arrays;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import org.junit.jupiter.api.Test;
//import org.springframework.security.crypto.codec.Base64;
//
//public class PurchaseTest {
//
//    @Test
//    void test3() {
//        String msg = "비밀번호: 2200";
//        Matcher matcher = Pattern.compile("비밀번호[:：\\s]*([0-9]{4})").matcher(msg);
//        if (matcher.find()) {
//            String password = matcher.group(1); // 괄호로 캡처한 숫자 4자리
//            System.out.println("ㅁㄴㅇㅁㄴㅇㅁ" + "-" + password);
//        }
//    }
//    @Test
//    void test2() {
//        String plain = "0000202506242129460122099905        18036501689160489                3000    도서문화상품권                  1비밀번호: 2468                  0                                0                                0                                0";
//        GalaxiaPurchaseResponse res = new GalaxiaPurchaseResponse();
//        int idx = 0;
//        res.setResponseCode(plain.substring(idx, idx += 4));
//        res.setApprovalCode(plain.substring(idx, idx += 32).trim());
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
//    }
//    @Test
//    void test () throws Exception {
//
//        GalaxiaCipher cipher = new Seed();
//        cipher.setKey(Base64.decode("cnlNUWJUd1FOMEFGeE5rcw==".getBytes("EUC-KR")));
//        cipher.setIV("1234567890123456".getBytes("EUC-KR"));
//        String base64EncryptedBody = "Qx12q6Zu2NaOhvK3y9Wy4l3u9CEpwbvmqX9F6c/Bjjaphze3L6APsv3Sd9yUfHtsGOK2l44/QjXjFXfxWl1ebo4KI4WH/yeMbmdlTI5Rr/r+8r2oyB9Ksbl2ZIH/s0dcMq92CZKN1Stiih8VvLoQQ428/RKvbQu6UwSExkKP5aYubquwnEX+YbNJOfatd4ksti3K6u7vBhDhYqRSeJCiyqYWaX9EgqMbWJZrzarspdrccSCKXJd9EBbwMko4TTmBQptSUQrkrebRxlMgyIdkRC/dQNzTxk0K76XVCR/t1QW6vzTI6dylo0XAjkmHLRo+MdCT+D4z47GHnx88i4MlUA8hDPMZVBceW3os3Q73TLmPr3HLDhmTK/jAsCAsOLAW6+n5ayKjDcJUaPWB2owxPug9YesMWp0SXOQdQsUAvGfnEUReImdExxMUzqT9Tmkj35pvnyJntd15ItU20iF1x8BbzRFAF+lWwrCa+9YIyyX74ZuR5ulnPg/4LMz0kH7IiEUbqxjmHy4ClmokY5YTp5VQAG2XakyIhFOWKdEn0vE=";
//
//        BASE64Decoder decoder = new BASE64Decoder();
//        byte[] encryptedBytes = decoder.decodeBuffer(base64EncryptedBody);
//
//        // 복호화 후 불필요한 패딩 제거 (0x00 ~ 0x1F까지 제거)
//        byte[] decryptedBytes = cipher.decrypt(encryptedBytes);
//
//        // 바이트에서 실제 문자열로 사용 가능한 부분만 추출 (EUC-KR 기준 null byte 제거)
//        int length = 0;
//        for (int i = 0; i < decryptedBytes.length; i++) {
//            byte b = decryptedBytes[i];
//            if (b == 0x00) break; // null byte 만나면 멈춤
//            length++;
//        }
//        byte[] cleanBytes = Arrays.copyOfRange(decryptedBytes, 0, length);
//        String plainBody = new String(cleanBytes, "EUC-KR");
//
//        System.out.println(plainBody);
//    }
//
//}
