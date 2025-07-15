package com.pintoss.auth.client.billgate;

import com.galaxia.api.ConfigInfo;
import com.galaxia.api.ServiceBroker;
import com.galaxia.api.crypto.GalaxiaCipher;
import com.galaxia.api.crypto.Seed;
import com.galaxia.api.merchant.Message;
import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.api.support.exception.server.InternalServerException;
import com.pintoss.auth.core.payment.application.dto.PaymentApprovalRequest;
import com.pintoss.auth.core.payment.application.dto.PaymentApprovalResponse;
import com.pintoss.auth.core.payment.application.flow.external.PaymentApprover;
import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApproverImpl implements PaymentApprover {

    @Value("${galaxia.payment.config-file-path}")
    private String configFilePath;
    private final ApprovalResponseParserFactory responseParserFactory;

    @Override
    public PaymentApprovalResponse approval(PaymentApprovalRequest req) {
        try {
            // 1. 리소스에서 config.ini InputStream으로 읽기
            InputStream configInputStream = getClass().getClassLoader().getResourceAsStream(configFilePath);
            if (configInputStream == null) {
                log.error("[결제 승인 실패] 설정 파일 (config-prod.ini) 누락 - 주문 번호: {}, 서비스 코드={}", req.getOrderNo(), req.getServiceCode());
                throw new InternalServerException(ErrorCode.BILLGATE_CONFIG_FILE_NOT_FOUND);
            }

            File tempConfigFile = File.createTempFile("config", ".ini");
            tempConfigFile.deleteOnExit(); // JVM 종료 시 삭제
            try (FileOutputStream out = new FileOutputStream(tempConfigFile)) {
                configInputStream.transferTo(out);
            }

            String configPath = tempConfigFile.getAbsolutePath();

            ConfigInfo configInfo = new ConfigInfo(configPath, req.getServiceCode());

            GalaxiaCipher cipher = new Seed();
            cipher.setKey(configInfo.getKey().getBytes("EUC-KR"));
            cipher.setIV(configInfo.getIv().getBytes("EUC-KR"));
            // 1. message 문자열을 EUC-KR로 인코딩하여 바이트 배열로 반환한다.
            byte[] rawBytes = req.getMessage().getBytes("EUC-KR");
            // 2. 전문 길이 필드 (4바이트)를 제외한 나머지 바이트 배열을 생성한다.
            byte[] encryptedBody = new byte[rawBytes.length - 4];
            // 3. 전문 길이 필드 (4바이트)를 제외한 나머지 바이트 배열을 복사한다.
            System.arraycopy(rawBytes, 4, encryptedBody, 0, encryptedBody.length);

            Message reqMessage = new Message(encryptedBody, cipher);

            // invoke할 때 전문 길이 필드(4바이트)를 포함한 바이트 배열을 전달한다.
            ServiceBroker broker = new ServiceBroker(configPath, req.getServiceCode());
            Message resMessage = broker.invoke(reqMessage);
            Map<String, String> map = convertToStringMap((Hashtable<String, Object>) resMessage.getData());

            ApprovalResponseParser<?> parser = responseParserFactory.getParser(
                PaymentMethodType.fromServiceCode(req.getServiceCode()));
            return parser.parse(resMessage, map);
        } catch (Exception e) {
            log.error("[결제 승인 실패] 결제 승인 요청 처리 중 오류 발생 - orderNo: {}, serviceCode={}", req.getOrderNo(), req.getServiceCode());
            throw new InternalServerException(ErrorCode.PAYMENT_MESSAGE_NOT_GENERATED);
        }
    }

    private Map<String, String> convertToStringMap(Hashtable<String, Object> data) {
        Map<String, String> result = new HashMap<>();

        for(Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if(value != null) {
                String cleaned = value.toString().replaceAll("^\\[|\\]$", "");
                result.put(key, cleaned);
            }
        }
        return result;
    }
}
