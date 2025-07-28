package com.pintoss.auth;

import com.galaxia.api.util.ChecksumUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@SpringBootTest
@ActiveProfiles("test")
public class EncryptedTelTest {
    @Test
    void test() throws Exception {
        String tel = "01020517426";
        // EUC-KR 디코딩 필요 시 처리
        String input = new String(tel.getBytes(StandardCharsets.ISO_8859_1), Charset.forName("EUC-KR"));

        System.out.println(ChecksumUtil.genCheckSum(input));
    }
    @Test
    void test2() throws Exception {
        System.out.println(ChecksumUtil.diffCheckSum("fcc93fb00b2f4d2ffac4ee5b4e2f0a681121272c", "01020517426"));
    }

}
