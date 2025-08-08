package com.pintoss.auth.core.voucher.application.flow.reader;

import com.pintoss.auth.core.voucher.application.repository.VoucherIssuerRepository;
import com.pintoss.auth.core.voucher.domain.VoucherIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherIssuerReader {

    private final VoucherIssuerRepository voucherIssuerRepository;

    public VoucherIssuer getOrElseThrow(Long voucherIssuerId) {
        return voucherIssuerRepository.findById(voucherIssuerId);
    }

}
