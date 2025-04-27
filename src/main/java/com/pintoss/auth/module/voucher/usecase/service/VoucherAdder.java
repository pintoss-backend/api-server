package com.pintoss.auth.module.voucher.usecase.service;

import com.pintoss.auth.module.voucher.model.Voucher;
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
