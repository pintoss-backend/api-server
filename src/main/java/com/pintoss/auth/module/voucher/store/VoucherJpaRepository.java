package com.pintoss.auth.module.voucher.store;

import com.pintoss.auth.module.voucher.store.VoucherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherJpaRepository extends JpaRepository<VoucherEntity, Long> {

}
