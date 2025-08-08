package com.pintoss.auth.core.voucher.application.flow.writer;

import com.pintoss.auth.core.voucher.domain.VoucherIssuerPaymentMethod;
import com.pintoss.auth.core.voucher.application.repository.VoucherIssuerPaymentMethodRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VoucherIssuerPaymentMethodAdder {

    private final VoucherIssuerPaymentMethodRepository voucherIssuerPaymentMethodRepository;

    public VoucherIssuerPaymentMethodAdder(
        VoucherIssuerPaymentMethodRepository voucherIssuerPaymentMethodRepository) {
        this.voucherIssuerPaymentMethodRepository = voucherIssuerPaymentMethodRepository;
    }

    public void saveAll(List<VoucherIssuerPaymentMethod> discounts) {
        voucherIssuerPaymentMethodRepository.saveAll(discounts);
    }
}
