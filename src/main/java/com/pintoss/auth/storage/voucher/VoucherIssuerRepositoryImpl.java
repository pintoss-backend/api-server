package com.pintoss.auth.storage.voucher;

import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerDetailResult;
import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerResult;
import com.pintoss.auth.core.voucher.application.repository.VoucherIssuerRepository;
import com.pintoss.auth.core.voucher.domain.VoucherIssuer;
import com.pintoss.auth.storage.voucher.jpa.VoucherIssuerJpaRepository;
import com.pintoss.auth.storage.voucher.jpa.entity.VoucherIssuerEntity;
import com.pintoss.auth.storage.voucher.querydsl.VoucherIssuerQueryDslRepository;
import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public VoucherIssuer findById(Long voucherIssuerId) {
        VoucherIssuerEntity entity = jpaRepository.findById(voucherIssuerId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND, "상품권 발급처를 찾을 수 없습니다."));

        return entity.toDomain();
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
        return queryDslRepository.fetchDetail(voucherIssuerId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND, "상품권 발급처를 찾을 수 없습니다."));
    }
}
