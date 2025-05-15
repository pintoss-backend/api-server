package com.pintoss.auth.module.user.api;

import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.module.user.api.dto.SendCodeRequest;
import com.pintoss.auth.module.user.usecase.SendCodeUseCase;
import com.pintoss.auth.module.user.usecase.VerifyCodeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/password-recovery")
@RequiredArgsConstructor
public class PasswordRecoveryController {

    private final VerifyCodeUseCase verifyCodeUseCase;
    private final SendCodeUseCase sendCodeUseCase;

    @PostMapping("/verify-user")
    public ApiResponse<Void> verifyUser(){
        return ApiResponse.ok(null);
    }
    @PostMapping("/send-code")
    public ApiResponse<Void> sendCode(@RequestBody SendCodeRequest request){
        sendCodeUseCase.sendCode(request.getEmail(), request.getName(), request.getPhone());
        return ApiResponse.ok(null);
    }
    @PostMapping("/verify-code")
    public ApiResponse<Void> verifyCode(){
        verifyCodeUseCase.verifyCode();
        return ApiResponse.ok(null);
    }
    @PostMapping("/reset")
    public ApiResponse<Void> passwordReset(){
        return ApiResponse.ok(null);
    }

}
