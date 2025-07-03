package com.pintoss.auth.core.voucher.usecase.service;

import com.pintoss.auth.core.voucher.model.VoucherIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherIssuerAdder {

    private final VoucherIssuerRepository voucherIssuerRepository;


    public void add(VoucherIssuer voucherIssuer) {
        voucherIssuerRepository.save(voucherIssuer);
    }
}
