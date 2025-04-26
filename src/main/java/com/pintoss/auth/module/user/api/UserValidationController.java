package com.pintoss.auth.module.user.api;

import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.module.user.api.dto.FindAccountResponse;
import com.pintoss.auth.module.user.usecase.FindUserAccountUseCase;
import com.pintoss.auth.module.user.usecase.UserValidationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserValidationController {

    private final UserValidationUseCase userValidationUseCase;
    private final FindUserAccountUseCase findUserAccountUseCase;

    @GetMapping("/check-id")
    public ApiResponse<Boolean> checkEmailDuplicate(@RequestParam(name = "email") String email) {
        Boolean rs = userValidationUseCase.checkEmailDuplicate(email);
        return ApiResponse.ok(rs);
    }

    @GetMapping("/check-phone")
    public ApiResponse<Boolean> checkPhoneDuplicate(@RequestParam(name = "phone") String phone) {
        Boolean rs = userValidationUseCase.checkPhoneDuplicate(phone);
        return ApiResponse.ok(rs);
    }

    @GetMapping("/find_id")
    public ApiResponse<FindAccountResponse> findAccount(@RequestParam(name = "name") String name, @RequestParam(name = "phone") String phone) {
        String account = findUserAccountUseCase.findAccount(name, phone);

        return ApiResponse.ok(new FindAccountResponse(account));
    }

}
