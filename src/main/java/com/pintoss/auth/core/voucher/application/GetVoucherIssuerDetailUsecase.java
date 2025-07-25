package com.pintoss.auth.core.voucher.application;

import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerDetailResult;
import com.pintoss.auth.core.voucher.application.flow.reader.VoucherIssuerProjectionReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetVoucherIssuerDetailUsecase {

    private final VoucherIssuerProjectionReader voucherIssuerProjectionReader;

    // 2. 상품권 상세 정보 조회
    @Transactional(readOnly = true)
    public VoucherIssuerDetailResult getVoucherIssuerDetail(Long voucherIssuerId) {
        return voucherIssuerProjectionReader.fetchDetail(voucherIssuerId);
    }

}
