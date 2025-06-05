package com.pintoss.auth.api.user;

import com.pintoss.auth.api.user.dto.PasswordResetRequest;
import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.module.user.application.PasswordResetUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/password-recovery")
@RequiredArgsConstructor
public class PasswordRecoveryController {

    private final PasswordResetUseCase passwordResetUseCase;

    @PostMapping("/reset")
    public ApiResponse<Void> passwordReset(@RequestBody PasswordResetRequest req){
        passwordResetUseCase.passwordReset(req.getEmail(), req.getName(), req.getPhone());
        return ApiResponse.ok(null);
    }
}
