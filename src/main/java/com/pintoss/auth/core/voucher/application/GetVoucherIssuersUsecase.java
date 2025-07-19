package com.pintoss.auth.core.voucher.application;

import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerResult;
import com.pintoss.auth.core.voucher.application.flow.reader.VoucherIssuerProjectionReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetVoucherIssuersUsecase {

    private final VoucherIssuerProjectionReader voucherIssuerProjectionReader;

    public List<VoucherIssuerResult> getVoucherIssuers() {
        return voucherIssuerProjectionReader.fetchSummaryList();
    }

}
