package com.pintoss.auth.storage.voucher.jpa;

import com.pintoss.auth.storage.voucher.jpa.entity.VoucherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherJpaRepository extends JpaRepository<VoucherEntity, Long> {

}
