package com.pintoss.auth.module.payment.integration;

import com.galaxia.api.ConfigInfo;
import com.galaxia.api.ServiceBroker;
import com.galaxia.api.crypto.GalaxiaCipher;
import com.galaxia.api.crypto.Seed;
import com.galaxia.api.merchant.Message;
import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.module.payment.application.flow.PaymentApprovalService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentApprovalClient implements PaymentApprovalService {

    @Override
    public PaymentApprovalResponse approval(String serviceCode, String orderNo, String message) {
        try {
            // 1. 리소스에서 config.ini InputStream으로 읽기
            InputStream configInputStream = getClass().getClassLoader().getResourceAsStream("config.ini");
            if (configInputStream == null) {
                throw new IllegalStateException("config.ini not found in classpath");
            }

            // 2. 임시 파일로 복사 (ServiceBroker는 실제 경로 필요)
            File tempConfigFile = File.createTempFile("config", ".ini");
            tempConfigFile.deleteOnExit(); // JVM 종료 시 삭제
            try (FileOutputStream out = new FileOutputStream(tempConfigFile)) {
                configInputStream.transferTo(out);
            }

            String configPath = tempConfigFile.getAbsolutePath();
//            String configPath = getClass().getClassLoader().getResource("config.ini").getPath();
//
            ConfigInfo configInfo = new ConfigInfo(configPath, serviceCode);
            // TODO : 서비스 코드에 따라 휴대폰 결제인지, 신용카드 결제인지 확인한다.
            GalaxiaCipher cipher = new Seed();
            cipher.setKey(configInfo.getKey().getBytes("EUC-KR"));
            cipher.setIV(configInfo.getIv().getBytes("EUC-KR"));
            System.out.println(orderNo);
            // 1. message 문자열을 EUC-KR로 인코딩하여 바이트 배열로 반환한다.
            byte[] rawBytes = message.getBytes("EUC-KR");
            // 2. 전문 길이 필드 (4바이트)를 제외한 나머지 바이트 배열을 생성한다.
            byte[] encryptedBody = new byte[rawBytes.length - 4];
            // 3. 전문 길이 필드 (4바이트)를 제외한 나머지 바이트 배열을 복사한다.
            System.arraycopy(rawBytes, 4, encryptedBody, 0, encryptedBody.length);

            Message reqMessage = new Message(encryptedBody, cipher);

            // invoke할 때 전문 길이 필드(4바이트)를 포함한 바이트 배열을 전달한다.
            ServiceBroker broker = new ServiceBroker(configPath, serviceCode);
            Message resMessage = broker.invoke(reqMessage);
            // 승인 요청 응답 코드가 0000이 아니면
            if (!resMessage.getData().get("1002").toString().equals("[0000]")) {
                throw new BadRequestException(ErrorCode.PAYMENT_APPROVAL_FAILED);
            };
            String logString = resMessage.getLogString();
            System.out.println("응답 전문 : " + logString);

            return PaymentApprovalResponseParser.parse(logString);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
