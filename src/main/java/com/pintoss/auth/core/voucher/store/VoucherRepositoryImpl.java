package com.pintoss.auth.core.voucher.store;

import com.pintoss.auth.core.voucher.model.Voucher;
import com.pintoss.auth.core.voucher.usecase.service.VoucherRepository;
import java.util.List;
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
