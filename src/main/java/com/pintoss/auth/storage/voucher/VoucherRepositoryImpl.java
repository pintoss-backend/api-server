package com.pintoss.auth.storage.voucher;

import com.pintoss.auth.core.voucher.domain.Voucher;
import com.pintoss.auth.core.voucher.application.repository.VoucherRepository;
import java.util.List;

import com.pintoss.auth.storage.voucher.jpa.entity.VoucherEntity;
import com.pintoss.auth.storage.voucher.jpa.VoucherJpaRepository;
import com.pintoss.auth.storage.voucher.querydsl.VoucherQueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VoucherRepositoryImpl implements VoucherRepository {

    private final VoucherJpaRepository voucherJpaRepository;
    private final VoucherQueryDslRepository voucherQueryDslRepository;

    @Override
    public void saveAll(List<Voucher> vouchers) {
        List<VoucherEntity> entities = vouchers.stream().map(VoucherEntity::from)
            .toList();
        voucherJpaRepository.saveAll(entities);
    }

    @Override
    public List<Voucher> findAllByIds(List<Long> voucherIds) {
        return voucherQueryDslRepository.findAllByIds(voucherIds);
    }

}
