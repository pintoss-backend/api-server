package com.pintoss.auth.core.voucher.application.repository;

import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerPaymentMethodResult;
import com.pintoss.auth.core.voucher.domain.VoucherIssuerPaymentMethod;
import com.pintoss.auth.core.voucher.domain.VoucherIssuerPaymentMethods;
import java.util.List;

public interface VoucherIssuerPaymentMethodRepository {

    void saveAll(List<VoucherIssuerPaymentMethod> discounts);

    VoucherIssuerPaymentMethods findByVoucherIssuerId(Long voucherIssuerId);

    List<VoucherIssuerPaymentMethod> findAll();

    List<VoucherIssuerPaymentMethodResult> fetchVoucherIssuerPaymentDiscountResults();
}
