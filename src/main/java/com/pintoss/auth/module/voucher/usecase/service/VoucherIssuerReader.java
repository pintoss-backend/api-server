package com.pintoss.auth.module.voucher.usecase.service;

import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.module.voucher.store.VoucherIssuerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherIssuerReader {

    private final VoucherIssuerRepository voucherIssuerRepository;

    public VoucherIssuerEntity read(Long voucherIssuerId) {
        return voucherIssuerRepository.findById(voucherIssuerId)
            .orElseThrow(() -> new BadRequestException("VoucherEntity issuer not found"));
    }

}
