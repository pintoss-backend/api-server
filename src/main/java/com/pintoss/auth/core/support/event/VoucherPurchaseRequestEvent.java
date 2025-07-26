package com.pintoss.auth.core.support.event;

import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import com.pintoss.auth.core.voucher.application.dto.VoucherPurchaseCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoucherPurchaseRequestEvent {
    private final String orderNo;
    private final Long orderItemId;
    private final String transactionId;
    private final String mId;
    private final Long paymentPrice;
    private final Long salePrice;
    private final PaymentMethodType paymentMethodType;
    private final String productCode;

    public VoucherPurchaseCommand toCommand() {
        return new VoucherPurchaseCommand(
            orderNo,
            orderItemId,
            transactionId,
            mId,
            paymentPrice,
            salePrice,
            paymentMethodType,
            productCode
        );
    }
}
