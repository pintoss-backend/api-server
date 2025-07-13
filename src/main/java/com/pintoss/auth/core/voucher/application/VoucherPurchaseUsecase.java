package com.pintoss.auth.core.voucher.application;

import com.pintoss.auth.common.event.VoucherPurchaseCompletedEvent;
import com.pintoss.auth.core.voucher.application.dto.PurchaseResult;
import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import com.pintoss.auth.core.voucher.application.flow.external.Purchaser;
import com.pintoss.auth.core.voucher.application.flow.external.VoucherEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class VoucherPurchaseUsecase {
    private final Purchaser purchaser;
    private final VoucherEventPublisher eventPublisher;

    public VoucherPurchaseUsecase(Purchaser purchaser, VoucherEventPublisher eventPublisher) {
        this.purchaser = purchaser;
        this.eventPublisher = eventPublisher;
    }

    public void purchase(
        String orderNo,
        Long orderItemId,
        String transactionId,
        String mId,
        Long paymentPrice,
        Long salePrice,
        PaymentMethodType paymentMethodType,
        String productCode
        ) {

        PurchaseResult result = purchaser.purchase(
            orderNo,
            transactionId,
            mId,
            paymentPrice,
            paymentMethodType,
            salePrice,
            productCode
//                "1104501710200000"
        );

        eventPublisher.publish(
            new VoucherPurchaseCompletedEvent(
                result.isSuccess(),
                orderNo,
                orderItemId,
                result.getPinNo(),
                result.getApprovalCode()
            )
        );
    }
}
