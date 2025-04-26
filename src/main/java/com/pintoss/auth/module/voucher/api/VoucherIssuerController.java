package com.pintoss.auth.module.voucher.api;

import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.module.voucher.api.dto.RegisterVoucherIssuerRequest;
import com.pintoss.auth.module.voucher.usecase.FetchVoucherIssuerUseCase;
import com.pintoss.auth.module.voucher.usecase.RegisterVoucherIssuerUseCase;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerDetailResponse;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/voucher-issuer")
@RequiredArgsConstructor
public class VoucherIssuerController {

    private final RegisterVoucherIssuerUseCase registerUseCase;
    private final FetchVoucherIssuerUseCase fetchIssuerUseCase;

    @PostMapping
    public ApiResponse<Void> registerVoucherIssuer(@RequestBody @Valid RegisterVoucherIssuerRequest request) {
        registerUseCase.register(request);

        return ApiResponse.ok(null);
    }

    @GetMapping
    public ApiResponse<List<VoucherIssuerResponse>> getVoucherIssuers() {
        List<VoucherIssuerResponse> voucherIssuers = fetchIssuerUseCase.getVoucherIssuers();

        return ApiResponse.ok(voucherIssuers);
    }

    @GetMapping("/{voucherIssuerId}")
    public ApiResponse<VoucherIssuerDetailResponse> getVoucherIssuerDetail(@PathVariable(name = "voucherIssuerId") Long voucherIssuerId) {
        VoucherIssuerDetailResponse voucherIssuerDetail = fetchIssuerUseCase.getVoucherIssuerDetail(voucherIssuerId);

        return ApiResponse.ok(voucherIssuerDetail);
    }

}
