package com.pintoss.auth.module.voucher.execution;

import com.pintoss.auth.module.voucher.execution.domain.VoucherIssuer;
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
