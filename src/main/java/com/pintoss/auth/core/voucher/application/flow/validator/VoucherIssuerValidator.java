package com.pintoss.auth.core.voucher.application.flow.validator;

import com.pintoss.auth.core.voucher.application.repository.VoucherIssuerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherIssuerValidator {

    private final VoucherIssuerRepository voucherIssuerRepository;

    public boolean isIssuerNameDuplicated(String issuerName) {
        return voucherIssuerRepository.existsByName(issuerName);
    }

}
