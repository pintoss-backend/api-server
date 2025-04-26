package com.pintoss.auth.module.voucher.external.persistence;

import com.pintoss.auth.module.voucher.execution.VoucherIssuerRepository;
import com.pintoss.auth.module.voucher.execution.domain.VoucherIssuer;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VoucherIssuerRepositoryImpl implements VoucherIssuerRepository {

    private final VoucherIssuerJpaRepository jpaRepository;

    @Override
    public void save(VoucherIssuer voucherIssuer) {
        jpaRepository.save(voucherIssuer);
    }

    @Override
    public Optional<VoucherIssuer> findById(Long voucherIssuerId) {
        return jpaRepository.findById(voucherIssuerId);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }
}
