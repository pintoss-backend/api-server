package com.pintoss.auth.core.voucher.application.repository;

import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerDetailResult;
import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerResult;
import com.pintoss.auth.core.voucher.domain.VoucherIssuer;

import java.util.List;

public interface VoucherIssuerRepository {
    void save(VoucherIssuer voucherIssuer);

    boolean existsByName(String name);

    VoucherIssuer findById(Long voucherIssuerId);

    List<VoucherIssuerResult> fetchSummaryList();

    VoucherIssuerDetailResult fetchDetail(Long voucherIssuerId);
}
