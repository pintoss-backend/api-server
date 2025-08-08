package com.pintoss.auth.core.voucher.application.flow.reader;

import com.pintoss.auth.core.voucher.application.repository.VoucherIssuerPaymentMethodRepository;
import org.springframework.stereotype.Component;

@Component
public class VoucherIssuerPaymentMethodReader {

    private final VoucherIssuerPaymentMethodRepository voucherIssuerPaymentMethodRepository;

    public VoucherIssuerPaymentMethodReader(
        VoucherIssuerPaymentMethodRepository voucherIssuerPaymentMethodRepository) {
        this.voucherIssuerPaymentMethodRepository = voucherIssuerPaymentMethodRepository;
    }

    public void readAll() {
        voucherIssuerPaymentMethodRepository.findAll();
    }
}
