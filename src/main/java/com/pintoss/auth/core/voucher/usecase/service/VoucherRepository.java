package com.pintoss.auth.core.voucher.usecase.service;

import com.pintoss.auth.core.voucher.model.Voucher;
import java.util.List;

public interface VoucherRepository {

    void saveAll(List<Voucher> vouchers);

    List<Voucher> findAllByIds(List<Long> voucherIds);
}
