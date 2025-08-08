package com.pintoss.auth.core.voucher.domain;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class VoucherIssuerPaymentMethod {

    private Long id;
    private Long voucherIssuerId;
    private VoucherIssuerPaymentMethodType paymentMethod;
    private BigDecimal discountRate;

    public VoucherIssuerPaymentMethod(Long voucherIssuerId, VoucherIssuerPaymentMethodType paymentMethod, BigDecimal discountRate) {
        this.voucherIssuerId = voucherIssuerId;
        this.paymentMethod = paymentMethod;
        this.discountRate = discountRate;
    }

    public VoucherIssuerPaymentMethod(Long id, Long voucherIssuerId, VoucherIssuerPaymentMethodType paymentMethod, BigDecimal discountRate) {
        this.id = id;
        this.voucherIssuerId = voucherIssuerId;
        this.paymentMethod = paymentMethod;
        this.discountRate = discountRate;
    }
}
