package com.pintoss.auth.module.voucher.store;

import com.pintoss.auth.module.voucher.store.VoucherIssuerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherIssuerJpaRepository extends JpaRepository<VoucherIssuerEntity, Long> {

    boolean existsByName(String name);

}
