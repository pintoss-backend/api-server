package com.pintoss.auth.module.user.api;

import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.module.user.model.UserInfo;
import com.pintoss.auth.module.user.usecase.UserQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserQueryUseCase userQueryUseCase;

    @GetMapping("/info")
    @PreAuthorize("hasAuthority('USER')")
    public ApiResponse<UserInfo> getUserInfo() {
        UserInfo userInfo = userQueryUseCase.getUserInfo();
        return ApiResponse.ok(userInfo);
    }

}
