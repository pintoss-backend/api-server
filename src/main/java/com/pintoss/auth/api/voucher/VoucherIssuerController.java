package com.pintoss.auth.api.voucher;

import com.pintoss.auth.api.support.dto.ApiResponse;
import com.pintoss.auth.api.voucher.dto.RegisterVoucherIssuerPaymentMethodRequest;
import com.pintoss.auth.api.voucher.dto.RegisterVoucherIssuerRequest;
import com.pintoss.auth.core.voucher.application.RegisterVoucherIssuerPaymentMethodUsecase;
import com.pintoss.auth.core.voucher.application.GetVoucherIssuerDetailUsecase;
import com.pintoss.auth.core.voucher.application.GetVoucherIssuersUsecase;
import com.pintoss.auth.core.voucher.application.RegisterVoucherIssuerUsecase;
import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerDetailResult;
import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerResult;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final RegisterVoucherIssuerUsecase registerUseCase;
    private final GetVoucherIssuersUsecase getVoucherIssuersUsecase;
    private final GetVoucherIssuerDetailUsecase getVoucherIssuerDetailUsecase;
    private final RegisterVoucherIssuerPaymentMethodUsecase registerVoucherIssuerPaymentMethodUsecase;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ApiResponse<Void> registerVoucherIssuer(@RequestBody @Valid RegisterVoucherIssuerRequest request) {
        registerUseCase.register(request.to());
        return ApiResponse.ok(null);
    }

    @GetMapping
    public ApiResponse<List<VoucherIssuerResult>> getVoucherIssuers() {
        List<VoucherIssuerResult> result = getVoucherIssuersUsecase.getVoucherIssuers();

        return ApiResponse.ok(result);
    }

    @GetMapping("/{voucherIssuerId}")
    public ApiResponse<VoucherIssuerDetailResult> getVoucherIssuerDetail(@PathVariable(name = "voucherIssuerId") Long voucherIssuerId) {
        VoucherIssuerDetailResult result = getVoucherIssuerDetailUsecase.getVoucherIssuerDetail(voucherIssuerId);
        return ApiResponse.ok(result);
    }

    @PostMapping("/{voucherIssuerId}/payment-method")
    public ApiResponse<Void> registerVoucherIssuerPaymentMethod(@PathVariable(name = "voucherIssuerId") Long voucherIssuerId, @RequestBody @Valid
    RegisterVoucherIssuerPaymentMethodRequest request) {

        registerVoucherIssuerPaymentMethodUsecase.registerPaymentMethod(request.to(voucherIssuerId));
        return ApiResponse.ok(null);
    }

}
