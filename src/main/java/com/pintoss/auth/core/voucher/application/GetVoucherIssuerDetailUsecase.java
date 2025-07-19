package com.pintoss.auth.core.voucher.application;

import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerDetailResult;
import com.pintoss.auth.core.voucher.application.flow.reader.VoucherIssuerProjectionReader;
import org.springframework.stereotype.Service;

@Service
public class GetVoucherIssuerDetailUsecase {
    private final VoucherIssuerProjectionReader voucherIssuerProjectionReader;

    public GetVoucherIssuerDetailUsecase(VoucherIssuerProjectionReader voucherIssuerProjectionReader) {
        this.voucherIssuerProjectionReader = voucherIssuerProjectionReader;
    }

    // 2. 상품권 상세 정보 조회
    public VoucherIssuerDetailResult getVoucherIssuerDetail(Long voucherIssuerId) {
        return voucherIssuerProjectionReader.fetchDetail(voucherIssuerId);
    }

}
