package com.pintoss.auth.core.voucher.usecase.service;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.NotFoundException;
import com.pintoss.auth.core.voucher.usecase.dto.VoucherIssuerDetailResult;
import com.pintoss.auth.core.voucher.usecase.dto.VoucherIssuerResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * UI 렌더링용 데이터 조회 (DTO 나 Projection 조회)
 * 화면에 필요한 일부 데이터를 조회하는 용도 네이밍을 정의한다.
 * fetch, read, query, search
* */
@Component
@RequiredArgsConstructor
public class VoucherIssuerFetcher {

    private final VoucherIssuerRepository voucherIssuerRepository;

    public List<VoucherIssuerResult> fetchSummaryList() {
        return voucherIssuerRepository.fetchSummaryList();
    }

    public VoucherIssuerDetailResult fetchDetail(Long voucherIssuerId) {
        return voucherIssuerRepository.fetchDetail(voucherIssuerId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND, "상품권 발급처를 찾을 수 없습니다."));
    }
}
