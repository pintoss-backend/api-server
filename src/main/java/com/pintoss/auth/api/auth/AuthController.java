package com.pintoss.auth.api.auth;

import com.pintoss.auth.api.auth.dto.LoginRequest;
import com.pintoss.auth.api.auth.dto.LoginResponse;
import com.pintoss.auth.api.auth.dto.RegisterRequest;
import com.pintoss.auth.api.auth.dto.ReissueResponse;
import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.common.util.HttpServletUtils;
import com.pintoss.auth.core.user.application.LoginUseCase;
import com.pintoss.auth.core.user.application.RegisterUseCase;
import com.pintoss.auth.core.user.application.ReissueUseCase;
import com.pintoss.auth.core.user.application.dto.LoginResult;
import com.pintoss.auth.core.user.application.dto.ReissueCommand;
import com.pintoss.auth.core.user.application.dto.ReissueResult;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;
    private final ReissueUseCase reissueUseCase;
    private final HttpServletUtils servletUtils;

    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody @Valid RegisterRequest request) {
        registerUseCase.register(request.to());

        return ApiResponse.ok(null);
    }


    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request, HttpServletResponse servletResponse) {
        LoginResult result = loginUseCase.login(request.to());

        servletUtils.addCookie(servletResponse, "RefreshToken", result.getRefreshToken(), (int) 1000000000L);

        return ApiResponse.ok(new LoginResponse(result.getAccessToken()));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/reissue")
    public ApiResponse<ReissueResponse> reissue(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        String refreshToken = servletUtils.getCookie(servletRequest, "RefreshToken")
            .map(Cookie::getValue)
            .orElseThrow(() -> new BadRequestException(ErrorCode.AUTH_MISSING_REFRESH_TOKEN));

        ReissueCommand command = new ReissueCommand(refreshToken);
        ReissueResult result = reissueUseCase.reissue(command);

        servletUtils.addCookie(servletResponse, "RefreshToken", result.getRefreshToken(), (int) 1000000000L);

        return ApiResponse.ok(new ReissueResponse(result.getAccessToken()));
    }

}
