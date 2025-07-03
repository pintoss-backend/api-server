package com.pintoss.auth.core.order.application.flow;

import com.pintoss.auth.core.order.domain.RefundResult;

public interface VoucherRefundProcessor {
    RefundResult refund(String orderNo, String approvalCode);
}
