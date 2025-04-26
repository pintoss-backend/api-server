package com.pintoss.auth.module.voucher.external.persistence;

import com.pintoss.auth.module.voucher.execution.domain.VoucherIssuer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherIssuerJpaRepository extends JpaRepository<VoucherIssuer, Long> {

    boolean existsByName(String name);

}
