package com.pintoss.auth.storage.voucher;

import com.pintoss.auth.core.voucher.domain.VoucherIssuer;
import com.pintoss.auth.core.voucher.application.repository.VoucherIssuerRepository;
import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerDetailResult;
import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerResult;
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
        VoucherIssuerEntity voucherIssuerEntity = VoucherIssuerEntity.from(voucherIssuer);
        jpaRepository.save(voucherIssuerEntity);
    }

    @Override
    public Optional<VoucherIssuerEntity> findById(Long voucherIssuerId) {
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
    public Optional<VoucherIssuerDetailResult> fetchDetail(Long voucherIssuerId) {
        return queryDslRepository.fetchDetail(voucherIssuerId);
    }
}
