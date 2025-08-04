package com.pintoss.auth;

import com.pintoss.auth.core.user.application.flow.external.MailSender;
import com.pintoss.auth.core.user.domain.vo.Password;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public MailSender fakeMailSender() {
        return new MailSender() {
            @Override
            public void sendTemporaryPassword(String to, Password password) {
                System.out.println("[TEST CONFIG] 메일 전송됨: " + to + " / " + password.getValue());
            }
        };
    }
}
