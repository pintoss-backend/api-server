package com.pintoss.auth.core.voucher.application.flow.writer;

import com.pintoss.auth.core.voucher.application.repository.VoucherIssuerRepository;
import com.pintoss.auth.core.voucher.domain.VoucherIssuer;
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
