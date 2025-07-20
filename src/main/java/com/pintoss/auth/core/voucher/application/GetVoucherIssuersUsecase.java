package com.pintoss.auth.core.voucher.application;

import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerResult;
import com.pintoss.auth.core.voucher.application.flow.reader.VoucherIssuerProjectionReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetVoucherIssuersUsecase {

    private final VoucherIssuerProjectionReader voucherIssuerProjectionReader;

    @Transactional(readOnly = true)
    public List<VoucherIssuerResult> getVoucherIssuers() {
        return voucherIssuerProjectionReader.fetchSummaryList();
    }

}
