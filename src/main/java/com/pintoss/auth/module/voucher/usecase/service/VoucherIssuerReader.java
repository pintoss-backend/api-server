package com.pintoss.auth.module.voucher.usecase.service;

import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.module.voucher.model.VoucherIssuer;
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
