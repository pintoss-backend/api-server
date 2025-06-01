package com.pintoss.auth.module.order.application.flow;

import com.pintoss.auth.module.order.domain.RefundResult;

public interface VoucherRefundProcessor {
    RefundResult refund(String orderNo, String approvalCode);
}
