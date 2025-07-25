package com.pintoss.auth.core.voucher.application.flow.external;

import com.pintoss.auth.core.voucher.application.dto.PurchaseResult;
import com.pintoss.auth.core.voucher.application.dto.VoucherPurchaseCommand;

public interface Purchaser {
    PurchaseResult purchase(VoucherPurchaseCommand command);
}
