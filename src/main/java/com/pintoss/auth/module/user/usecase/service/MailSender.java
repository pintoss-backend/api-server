package com.pintoss.auth.module.user.usecase.service;

public interface MailSender {

    // 이 메서드는 비밀번호 인증코드르 보내기 위한 메서드인데 메서드 네이밍을 직관적으로 수정해줘
    void sendVerificationCodeForPasswordReset(String to);

}
