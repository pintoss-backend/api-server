package com.pintoss.auth.storage.voucher.jpa;

import com.pintoss.auth.storage.voucher.jpa.entity.VoucherIssuerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherIssuerJpaRepository extends JpaRepository<VoucherIssuerEntity, Long> {

    boolean existsByName(String name);

}
