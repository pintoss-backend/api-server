package com.pintoss.auth.module.voucher.usecase;

import com.pintoss.auth.module.voucher.usecase.service.VoucherIssuerFetcher;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerDetailResult;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FetchVoucherIssuerUseCase {

    private final VoucherIssuerFetcher voucherIssuerFetcher;

    // 1. 상품권 리스트 조회
    public List<VoucherIssuerResult> getVoucherIssuers() {
        return voucherIssuerFetcher.fetchSummaryList();
    }

    // 2. 상품권 상세 정보 조회
    public VoucherIssuerDetailResult getVoucherIssuerDetail(Long voucherIssuerId) {
        return voucherIssuerFetcher.fetchDetail(
            voucherIssuerId);
    }

}
