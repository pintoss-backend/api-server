package com.pintoss.auth.module.voucher.execution;

import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerDetailResponse;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerResponse;
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

    public List<VoucherIssuerResponse> fetchSummaryList() {
        return voucherIssuerRepository.fetchSummaryList();
    }

    public VoucherIssuerDetailResponse fetchDetail(Long voucherIssuerId) {
        return voucherIssuerRepository.fetchDetail(voucherIssuerId);
    }
}
