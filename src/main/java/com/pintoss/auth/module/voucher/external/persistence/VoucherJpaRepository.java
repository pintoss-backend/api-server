package com.pintoss.auth.module.voucher.external.persistence;

import com.pintoss.auth.module.voucher.execution.domain.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherJpaRepository extends JpaRepository<Voucher, Long> {

}
