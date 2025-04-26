package com.pintoss.auth.module.voucher.usecase;

import com.pintoss.auth.module.voucher.execution.VoucherIssuerFetcher;
import com.pintoss.auth.module.voucher.execution.domain.VoucherIssuerDetailResult;
import com.pintoss.auth.module.voucher.execution.domain.VoucherIssuerResult;
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
        List<VoucherIssuerResult> result = voucherIssuerFetcher.fetchSummaryList();

        return result.stream()
            .map(r -> new VoucherIssuerResponse(r.getId(), r.getName(), r.getDiscount(),
                r.getContactInfo(), r.getDescription(), r.getPublisher(), r.getImageUrl(),
                r.getNote()))
            .toList();
    }

    // 2. 상품권 상세 정보 조회
    public VoucherIssuerDetailResponse getVoucherIssuerDetail(Long voucherIssuerId) {
        VoucherIssuerDetailResult result = voucherIssuerFetcher.fetchDetail(
            voucherIssuerId);

        return new VoucherIssuerDetailResponse(
            result.getId(),
            result.getName(),
            result.getDescription(),
            result.getDiscount(),
            result.getContactInfo(),
            result.getPublisher(),
            result.getNote(),
            result.getImageUrl(),
            result.getVouchers()
        );
    }

}
