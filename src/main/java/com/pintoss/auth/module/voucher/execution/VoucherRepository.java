package com.pintoss.auth.module.voucher.execution;

import com.pintoss.auth.module.voucher.execution.domain.Voucher;
import java.util.List;

public interface VoucherRepository {

    void saveAll(List<Voucher> vouchers);

    List<Voucher> findAllByIds(List<Long> voucherIds);
}
