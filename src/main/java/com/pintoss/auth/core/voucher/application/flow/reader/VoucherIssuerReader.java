package com.pintoss.auth.core.voucher.application.flow.reader;

import com.pintoss.auth.support.exception.BadRequestException;
import com.pintoss.auth.core.voucher.application.repository.VoucherIssuerRepository;
import com.pintoss.auth.storage.voucher.VoucherIssuerEntity;
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
