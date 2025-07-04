package com.pintoss.auth.core.voucher.application.service;

import com.pintoss.auth.core.voucher.domain.VoucherIssuer;
import com.pintoss.auth.storage.voucher.VoucherIssuerEntity;
import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerDetailResult;
import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerResult;
import java.util.List;
import java.util.Optional;

public interface VoucherIssuerRepository {
    void save(VoucherIssuer voucherIssuer);

    boolean existsByName(String name);

    Optional<VoucherIssuerEntity> findById(Long voucherIssuerId);

    List<VoucherIssuerResult> fetchSummaryList();

    Optional<VoucherIssuerDetailResult> fetchDetail(Long voucherIssuerId);
}
