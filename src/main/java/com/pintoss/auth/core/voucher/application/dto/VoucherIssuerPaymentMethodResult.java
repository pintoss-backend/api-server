package com.pintoss.auth.core.voucher.application.dto;

import com.pintoss.auth.core.voucher.domain.VoucherIssuerPaymentMethodType;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoucherIssuerPaymentMethodResult {

    private Long voucherIssuerId;
    private VoucherIssuerPaymentMethodType paymentMethod;
    private BigDecimal discountRate;

    public VoucherIssuerPaymentMethodResult(Long voucherIssuerId, VoucherIssuerPaymentMethodType paymentMethod, BigDecimal discountRate) {
        this.voucherIssuerId = voucherIssuerId;
        this.paymentMethod = paymentMethod;
        this.discountRate = discountRate;
    }
}
