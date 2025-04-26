package com.pintoss.auth.module.voucher.execution;

import com.pintoss.auth.module.voucher.execution.domain.VoucherIssuer;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerDetailResponse;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerResponse;
import java.util.List;
import java.util.Optional;

public interface VoucherIssuerRepository {
    void save(VoucherIssuer voucherIssuer);

    boolean existsByName(String name);

    Optional<VoucherIssuer> findById(Long voucherIssuerId);

    List<VoucherIssuerResponse> fetchSummaryList();

    VoucherIssuerDetailResponse fetchDetail(Long voucherIssuerId);
}
