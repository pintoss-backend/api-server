package com.pintoss.auth.module.user.api;

import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.module.user.api.dto.OAuth2Response;
import com.pintoss.auth.module.user.model.LoginType;
import com.pintoss.auth.module.user.usecase.OAuth2UseCase;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2UseCase oAuth2UseCase;

    @GetMapping("/login")
    public ResponseEntity<Void> getOAuth2LoginUrl(@RequestParam("loginType")LoginType loginType) {
        String loginUrl = oAuth2UseCase.getOAuth2LoginUrl(loginType);
        return ResponseEntity.status(302)
                .location(URI.create(loginUrl))
                .build();
    }

    @GetMapping("/callback/{loginType}")
    public ApiResponse<OAuth2Response> oauthCallback(@PathVariable(value = "loginType") LoginType loginType, @RequestParam("code") String code, HttpServletResponse servletResponse) throws IOException {
        OAuth2Response response = oAuth2UseCase.oauthLogin(loginType, code);

        return ApiResponse.ok(response);

//        if(response instanceof OAuth2SignupRequired) {
//            OAuth2SignupRequired signupRequired = (OAuth2SignupRequired) response;
//            servletResponse.sendRedirect("https://pin-toss.com/register?email="+ signupRequired.getEmail()+"&loginType="+loginType.toString());
//        } else if (response instanceof OAuth2LoginSuccess) {
//            OAuth2LoginSuccess loginSuccess = (OAuth2LoginSuccess) response;
//            servletResponse.sendRedirect("https://pin-toss.com/login/social?accessToken="+ loginSuccess.getAccessToken());
//        }
//        return null;
    }
}
