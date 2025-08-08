package com.pintoss.auth.core.voucher.application.flow.reader;

import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerPaymentMethodResult;
import com.pintoss.auth.core.voucher.application.repository.VoucherIssuerPaymentMethodRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VoucherIssuerPaymentMethodProjectionReader {

    private final VoucherIssuerPaymentMethodRepository voucherIssuerPaymentMethodRepository;

    public VoucherIssuerPaymentMethodProjectionReader(
        VoucherIssuerPaymentMethodRepository voucherIssuerPaymentMethodRepository) {
        this.voucherIssuerPaymentMethodRepository = voucherIssuerPaymentMethodRepository;
    }

    public List<VoucherIssuerPaymentMethodResult> fetchVoucherIssuerPaymentDiscountResults() {
        return voucherIssuerPaymentMethodRepository.fetchVoucherIssuerPaymentDiscountResults();
    }
}
