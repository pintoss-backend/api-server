package com.pintoss.auth.core.voucher.application.flow.writer;

import com.pintoss.auth.core.voucher.application.repository.VoucherRepository;
import com.pintoss.auth.core.voucher.domain.Voucher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VoucherAdder {

    private final VoucherRepository voucherRepository;

    public void addAll(List<Voucher> vouchers) {
        voucherRepository.saveAll(vouchers);
    }

}
