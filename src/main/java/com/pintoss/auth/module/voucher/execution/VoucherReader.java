package com.pintoss.auth.module.voucher.execution;

import com.pintoss.auth.module.voucher.execution.domain.Voucher;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherReader {

    private final VoucherRepository voucherRepository;

    public List<Voucher> readAll(List<Long> voucherIds) {
        return voucherRepository.findAllByIds(voucherIds);
    }


}
