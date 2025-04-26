package com.pintoss.auth.module.voucher.execution;

import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.module.voucher.execution.domain.VoucherIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherIssuerReader {

    private final VoucherIssuerRepository voucherIssuerRepository;

    public VoucherIssuer read(Long voucherIssuerId) {
        return voucherIssuerRepository.findById(voucherIssuerId)
            .orElseThrow(() -> new BadRequestException("Voucher issuer not found"));
    }

}
