package com.pintoss.auth.core.voucher.domain;

import com.pintoss.auth.core.support.exception.CoreErrorCode;
import com.pintoss.auth.core.support.exception.CoreException;
import java.util.List;

public class VoucherIssuerPaymentMethods {

    private final List<VoucherIssuerPaymentMethod> paymentMethods;

    public VoucherIssuerPaymentMethods(List<VoucherIssuerPaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<VoucherIssuerPaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void checkAlreadyRegistered(List<VoucherIssuerPaymentMethodType> paymentMethodTypes) {
        for (VoucherIssuerPaymentMethodType paymentMethodType : paymentMethodTypes) {
            boolean isRegistered = paymentMethods.stream()
                .anyMatch(discount -> discount.getPaymentMethod() == paymentMethodType);
            if (isRegistered) {
                throw new CoreException(CoreErrorCode.ALREADY_REGISTERED_PAYMENT_METHOD);
            }
        }
    }
}
