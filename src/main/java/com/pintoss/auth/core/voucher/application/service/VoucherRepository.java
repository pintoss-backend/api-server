package com.pintoss.auth.core.voucher.application.service;

import com.pintoss.auth.core.voucher.domain.Voucher;
import java.util.List;

public interface VoucherRepository {

    void saveAll(List<Voucher> vouchers);

    List<Voucher> findAllByIds(List<Long> voucherIds);
}
