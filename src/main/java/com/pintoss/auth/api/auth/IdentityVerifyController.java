package com.pintoss.auth.api.auth;

import com.pintoss.auth.api.auth.dto.IdentityVerifyPurpose;
import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.core.user.application.IdentityVerificationUseCase;
import com.pintoss.auth.core.user.application.dto.NiceEncryptedDataResult;
import com.pintoss.auth.core.user.application.dto.NiceVerificationResult;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nice")
public class IdentityVerifyController {

    private final IdentityVerificationUseCase identityVerificationUseCase;
    @GetMapping("/encrypted-data")
    public ApiResponse<NiceEncryptedDataResult> getEncryptedData(@RequestParam(name="purpose") IdentityVerifyPurpose purpose) {
        NiceEncryptedDataResult encryptedData = identityVerificationUseCase.getEncryptedData(purpose);
        return ApiResponse.ok(encryptedData);
    }

//    @GetMapping("/callback")
//    public ApiResponse<VerifyResponse> niceCallback(
//            @RequestParam(name = "token_version_id") String tokenVersionId,
//            @RequestParam(name = "enc_data") String encData,
//            @RequestParam(name = "integrity_value") String integrityValue
//    ) {
//        NiceVerificationServiceCommand command = new NiceVerificationServiceCommand(tokenVersionId, encData, integrityValue);
//
//        NiceVerificationResult verifyResult = niceAuthVerificationService.verify(command);
//
//        return ApiResponse.ok(VerifyResponse.from(verifyResult));
//    }
    @GetMapping("/callback")
    public void niceCallback(
        @RequestParam(name = "token_version_id") String tokenVersionId,
        @RequestParam(name = "enc_data") String encData,
        @RequestParam(name = "integrity_value") String integrityValue,
        HttpServletResponse response
    ) throws IOException {
        NiceVerificationResult result = identityVerificationUseCase.verify(tokenVersionId, encData, integrityValue);
        log.info("Verification Result - Name: {}, Tel: {}, Success: {}", result.getName(), result.getTel(), result.getIsSuccess());

        String redirectUrl = "";
        if(IdentityVerifyPurpose.SIGNUP.getCode().equals(result.getPurpose())){
            redirectUrl = String.format("https://pin-toss.com/register/nice?name=%s&tel=%s&success=%s", URLEncoder.encode(result.getName(), StandardCharsets.UTF_8.toString()), result.getTel(), result.getIsSuccess());
        } else if (IdentityVerifyPurpose.PASSWORD_RESET.getCode().equals(result.getPurpose())) {
            redirectUrl = String.format("https://pin-toss.com/password-reset/nice?name=%s&tel=%s&success=%s", URLEncoder.encode(result.getName(), StandardCharsets.UTF_8.toString()), result.getTel(), result.getIsSuccess());
        }

        response.sendRedirect(redirectUrl);
    }
}
