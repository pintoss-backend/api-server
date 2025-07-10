package com.pintoss.auth.core.voucher.application;

import com.pintoss.auth.core.voucher.application.flow.viewer.VoucherIssuerProjectionReader;
import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetVoucherIssuersUsecase {

    private final VoucherIssuerProjectionReader voucherIssuerProjectionReader;

    public List<VoucherIssuerResult> getVoucherIssuers() {
        return voucherIssuerProjectionReader.fetchSummaryList();
    }

}
