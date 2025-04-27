package com.pintoss.auth.module.voucher.usecase.service;

import com.pintoss.auth.module.voucher.model.VoucherIssuer;
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
