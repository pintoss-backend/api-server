package com.pintoss.auth.core.voucher.application.flow.reader;

import com.pintoss.auth.core.support.exception.CoreErrorCode;
import com.pintoss.auth.core.support.exception.CoreException;
import com.pintoss.auth.core.voucher.application.repository.VoucherIssuerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherIssuerValidator {

    private final VoucherIssuerRepository voucherIssuerRepository;

    public void validateIssuerNameDuplicate(String issuerName) {
        if (voucherIssuerRepository.existsByName(issuerName)) {
            throw new CoreException(CoreErrorCode.VOUCHER_ISSUER_ALREADY_EXISTS);
        }
    }

}
