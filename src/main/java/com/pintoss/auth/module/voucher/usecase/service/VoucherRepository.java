package com.pintoss.auth.module.voucher.usecase.service;

import com.pintoss.auth.module.voucher.model.Voucher;
import java.util.List;

public interface VoucherRepository {

    void saveAll(List<Voucher> vouchers);

    List<Voucher> findAllByIds(List<Long> voucherIds);
}
