package com.pintoss.auth.module.user.api;

import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.module.user.api.dto.PasswordUpdateRequest;
import com.pintoss.auth.module.user.model.UserInfo;
import com.pintoss.auth.module.user.usecase.PasswordUpdateUseCase;
import com.pintoss.auth.module.user.usecase.UserQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserQueryUseCase userQueryUseCase;
    private final PasswordUpdateUseCase passwordUpdateUseCase;

    @GetMapping("/info")
    @PreAuthorize("hasAuthority('USER')")
    public ApiResponse<UserInfo> getUserInfo() {
        UserInfo userInfo = userQueryUseCase.getUserInfo();
        return ApiResponse.ok(userInfo);
    }

    @PutMapping("/password")
    @PreAuthorize("hasAuthority('USER')")
    public ApiResponse<Void> updatePassword(@RequestBody PasswordUpdateRequest request) {
        passwordUpdateUseCase.updatePassword(request.getOriginPassword(), request.getNewPassword());
        return ApiResponse.ok(null);
    }

}
