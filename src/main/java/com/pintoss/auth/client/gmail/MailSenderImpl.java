package com.pintoss.auth.client.gmail;

import com.pintoss.auth.core.user.application.flow.external.MailSender;
import com.pintoss.auth.core.user.domain.vo.Password;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Profile("!test")
@Slf4j
public class MailSenderImpl implements MailSender {

    private final GmailClient gmailClient;

    /**
     * 임시 비밀번호를 이메일로 전송합니다.
     *
     * @param to                수신자 이메일 주소
     * @param password 생성된 임시 비밀번호
     */
    @Override
    public void sendTemporaryPassword(String to, Password password) {
        SendRequest request = SendRequest.builder()
            .sender("noreply@pin-toss.com")
            .receiver(to)
            .subject("[핀토스] 임시 비밀번호 발급 안내")
            .content("임시 비밀번호: " + password.getValue())
            .build();
        gmailClient.send(request);
        log.info("[메일 발송] 임시 비밀번호 발급 메일 전송 완료 - receiver={}, subject={}, traceId={}",
            to, request.getSubject(), UUID.randomUUID()); // traceId로 추적 가능하게
    }
}
