package com.pintoss.auth.module.voucher.api;

import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.module.voucher.api.dto.RegisterVoucherIssuerRequest;
import com.pintoss.auth.module.voucher.usecase.RegisterVoucherIssuerUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/voucher-issuer")
@RequiredArgsConstructor
public class VoucherIssuerController {

    private final RegisterVoucherIssuerUseCase registerUseCase;

    @PostMapping
    public ApiResponse<Void> registerVoucherIssuer(@RequestBody @Valid RegisterVoucherIssuerRequest request) {
        registerUseCase.register(request);

        return ApiResponse.ok(null);
    }

}
