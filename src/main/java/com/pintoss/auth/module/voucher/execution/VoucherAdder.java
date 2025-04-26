package com.pintoss.auth.module.voucher.execution;

import com.pintoss.auth.module.voucher.execution.domain.Voucher;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherAdder {

    private final VoucherRepository voucherRepository;


    public void add(List<Voucher> vouchers) {
        voucherRepository.saveAll(vouchers);
    }

}
