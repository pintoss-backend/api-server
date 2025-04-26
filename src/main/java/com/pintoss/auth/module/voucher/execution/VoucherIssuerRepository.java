package com.pintoss.auth.module.voucher.execution;

import com.pintoss.auth.module.voucher.execution.domain.VoucherIssuer;
import java.util.Optional;

public interface VoucherIssuerRepository {
    void save(VoucherIssuer voucherIssuer);

    boolean existsByName(String name);

    Optional<VoucherIssuer> findById(Long voucherIssuerId);
}
