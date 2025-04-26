package com.pintoss.auth.module.voucher.usecase;

import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.module.voucher.execution.VoucherIssuerFetcher;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerDetailResponse;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FetchVoucherIssuerUseCase {

    private final VoucherIssuerFetcher voucherIssuerFetcher;

    // 1. 상품권 리스트 조회
    public List<VoucherIssuerResponse> getVoucherIssuers() {
        // 상품권 발행사 리스트를 조회하는 로직을 구현합니다.
        // 예시: return voucherIssuerRepository.findAll();
        return voucherIssuerFetcher.fetchSummaryList();
    }

    // 2. 상품권 상세 정보 조회
    public VoucherIssuerDetailResponse getVoucherIssuerDetail(Long voucherIssuerId) {
        return voucherIssuerFetcher.fetchDetail(voucherIssuerId);
    }

}
