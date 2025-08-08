package com.pintoss.auth.storage.voucher;

import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerPaymentMethodResult;
import com.pintoss.auth.core.voucher.domain.VoucherIssuerPaymentMethod;
import com.pintoss.auth.core.voucher.application.repository.VoucherIssuerPaymentMethodRepository;
import com.pintoss.auth.core.voucher.domain.VoucherIssuerPaymentMethods;
import com.pintoss.auth.storage.voucher.jpa.entity.VoucherIssuerPaymentMethodEntity;
import com.pintoss.auth.storage.voucher.jpa.VoucherIssuerPaymentMethodJpaRepository;
import com.pintoss.auth.storage.voucher.querydsl.VoucherIssuerPaymentMethodQueryDslRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VoucherIssuerPaymentMethodRepositoryImpl implements
    VoucherIssuerPaymentMethodRepository {

    private final VoucherIssuerPaymentMethodJpaRepository voucherIssuerPaymentMethodJpaRepository;
    private final VoucherIssuerPaymentMethodQueryDslRepository voucherIssuerPaymentMethodQueryDslRepository;

    @Override
    public void saveAll(List<VoucherIssuerPaymentMethod> discounts) {
        List<VoucherIssuerPaymentMethodEntity> entities = discounts.stream()
            .map(VoucherIssuerPaymentMethodEntity::from).toList();
        // 저장
        voucherIssuerPaymentMethodJpaRepository.saveAll(entities);
    }

    @Override
    public List<VoucherIssuerPaymentMethod> findAll() {
        return voucherIssuerPaymentMethodJpaRepository.findAll()
            .stream()
            .map(VoucherIssuerPaymentMethodEntity::toDomain)
            .toList();
    }

    @Override
    public VoucherIssuerPaymentMethods findByVoucherIssuerId(Long voucherIssuerId) {
        List<VoucherIssuerPaymentMethodEntity> voucherIssuerPaymentDiscountEntities = voucherIssuerPaymentMethodJpaRepository.findByVoucherIssuerId(
            voucherIssuerId);

        List<VoucherIssuerPaymentMethod> domains = voucherIssuerPaymentDiscountEntities.stream()
            .map(VoucherIssuerPaymentMethodEntity::toDomain).toList();
        return new VoucherIssuerPaymentMethods(domains);
    }

    @Override
    public List<VoucherIssuerPaymentMethodResult> fetchVoucherIssuerPaymentDiscountResults() {
        return voucherIssuerPaymentMethodQueryDslRepository
            .fetchVoucherIssuerPaymentDiscountResult();
    }
}
