package com.pintoss.auth.module.voucher.external.persistence;

import com.pintoss.auth.module.voucher.usecase.service.VoucherIssuerRepository;
import com.pintoss.auth.module.voucher.model.VoucherIssuer;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerDetailResult;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerResult;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
/**
 * "비즈니스 도메인 객체 조회" = find, get
 * "UI 표시용 가공된 데이터 조회" = fetch, query, search, read
* */
@Repository
@RequiredArgsConstructor
public class VoucherIssuerRepositoryImpl implements VoucherIssuerRepository {

    private final VoucherIssuerJpaRepository jpaRepository;
    private final VoucherIssuerQueryDslRepository queryDslRepository;

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

    @Override
    public List<VoucherIssuerResult> fetchSummaryList() {
        return queryDslRepository.fetchSummaryList();
    }

    @Override
    public VoucherIssuerDetailResult fetchDetail(Long voucherIssuerId) {
        return queryDslRepository.fetchDetail(voucherIssuerId);
    }
}
