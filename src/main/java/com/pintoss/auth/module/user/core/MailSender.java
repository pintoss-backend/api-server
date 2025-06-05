package com.pintoss.auth.module.user.core;

public interface MailSender {

    /**
     * 임시 비밀번호를 이메일로 전송합니다.
     * @param to 수신자 이메일 주소
     * @param temporaryPassword 생성된 임시 비밀번호
     */
    void sendTemporaryPassword(String to, String temporaryPassword);

}
