package com.pintoss.auth.core.voucher.application.flow.reader;

import com.pintoss.auth.api.support.exception.client.NotFoundException;
import com.pintoss.auth.core.voucher.application.repository.VoucherIssuerRepository;
import com.pintoss.auth.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherIssuerValidator {

    private final VoucherIssuerRepository voucherIssuerRepository;

    public void validateIssuerNameDuplicate(String issuerName) {
        if (!voucherIssuerRepository.existsByName(issuerName)) {
            throw new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND, "상품권 발급처를 찾을 수 없습니다.");
        }
    }

}
