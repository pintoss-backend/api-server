package com.pintoss.auth.core.support.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoucherPurchaseCompletedEvent {
    private final boolean isSuccess;
    private final String orderNo;
    private final Long orderItemId;
    private final String pinNo;
    private final String approvalCode;
}
