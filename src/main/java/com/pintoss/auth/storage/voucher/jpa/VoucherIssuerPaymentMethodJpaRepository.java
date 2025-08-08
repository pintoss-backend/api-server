package com.pintoss.auth.storage.voucher.jpa;

import com.pintoss.auth.storage.voucher.jpa.entity.VoucherIssuerPaymentMethodEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherIssuerPaymentMethodJpaRepository extends
    JpaRepository<VoucherIssuerPaymentMethodEntity, Long> {

    List<VoucherIssuerPaymentMethodEntity> findByVoucherIssuerId(Long voucherIssuerId);
}
