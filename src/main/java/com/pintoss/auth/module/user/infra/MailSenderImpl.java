package com.pintoss.auth.module.user.infra;

import com.pintoss.auth.module.user.infra.mail.GmailClient;
import com.pintoss.auth.module.user.infra.mail.SendRequest;
import com.pintoss.auth.module.user.usecase.service.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {

    private final GmailClient gmailClient;

    @Override
    public void sendVerificationCodeForPasswordReset(String to) {
        SendRequest request = SendRequest.builder()
                .sender("noreply@pin-toss.com")
                .receiver(to)
                .subject("제목")
                .content("본문")
                .build();
        gmailClient.send(request);
        System.out.println("Sending email to: " + to);
        System.out.println("Subject: " + "제목");
        System.out.println("Body: " + "본문");
    }
}
