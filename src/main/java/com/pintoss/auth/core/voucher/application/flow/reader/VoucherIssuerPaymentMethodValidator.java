package com.pintoss.auth.core.voucher.application.flow.reader;

import com.pintoss.auth.core.voucher.domain.VoucherIssuerPaymentMethodType;
import com.pintoss.auth.core.voucher.application.repository.VoucherIssuerPaymentMethodRepository;
import com.pintoss.auth.core.voucher.domain.VoucherIssuerPaymentMethods;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VoucherIssuerPaymentMethodValidator {

    private final VoucherIssuerPaymentMethodRepository voucherIssuerPaymentMethodRepository;

    public VoucherIssuerPaymentMethodValidator(
        VoucherIssuerPaymentMethodRepository voucherIssuerPaymentMethodRepository) {
        this.voucherIssuerPaymentMethodRepository = voucherIssuerPaymentMethodRepository;
    }


    public void checkAlreadyRegistered(Long voucherIssuerId, List<VoucherIssuerPaymentMethodType> paymentMethodTypes) {
        VoucherIssuerPaymentMethods discounts = voucherIssuerPaymentMethodRepository.findByVoucherIssuerId(
            voucherIssuerId);

        discounts.checkAlreadyRegistered(paymentMethodTypes);
    }
}
