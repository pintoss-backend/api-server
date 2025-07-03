package com.pintoss.auth.core.user.application;

import com.pintoss.auth.core.user.core.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyCodeUseCase {

    private final UserReader userReader;


    public void verifyCode() {
    }

    public void resetPassword(String email, String name, String phone, String encryptedToken,
        String newPassword) {

        // 1. 대칭키로 암호화를 해독한다.
        // 2. 입력값과 해독된 값으로 User를 조회한다. - 없으면 에러
        // 3. User의 값과 해독된 값이 일치하는지 확인한다. - 일치하지 않으면 에러
        // 4. 비밀번호를 변경한다.

    }
}
