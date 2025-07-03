package com.pintoss.auth.core.voucher.store;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherIssuerJpaRepository extends JpaRepository<VoucherIssuerEntity, Long> {

    boolean existsByName(String name);

}
