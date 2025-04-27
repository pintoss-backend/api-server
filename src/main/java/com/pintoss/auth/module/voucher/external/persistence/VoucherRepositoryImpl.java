package com.pintoss.auth.module.voucher.external.persistence;

import com.pintoss.auth.module.voucher.usecase.service.VoucherRepository;
import com.pintoss.auth.module.voucher.model.Voucher;
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
        voucherJpaRepository.saveAll(vouchers);
    }

    @Override
    public List<Voucher> findAllByIds(List<Long> voucherIds) {
        return voucherQueryDslRepository.findAllByIds(voucherIds);
    }

}
