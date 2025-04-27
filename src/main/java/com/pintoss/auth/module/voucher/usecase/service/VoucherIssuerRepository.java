package com.pintoss.auth.module.voucher.usecase.service;

import com.pintoss.auth.module.voucher.model.VoucherIssuer;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerDetailResult;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerResult;
import java.util.List;
import java.util.Optional;

public interface VoucherIssuerRepository {
    void save(VoucherIssuer voucherIssuer);

    boolean existsByName(String name);

    Optional<VoucherIssuer> findById(Long voucherIssuerId);

    List<VoucherIssuerResult> fetchSummaryList();

    VoucherIssuerDetailResult fetchDetail(Long voucherIssuerId);
}
