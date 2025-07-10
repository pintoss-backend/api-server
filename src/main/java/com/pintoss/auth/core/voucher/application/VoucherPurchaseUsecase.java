package com.pintoss.auth.core.voucher.application;

import com.pintoss.auth.client.galaxia.PurchaseApiClient;
import com.pintoss.auth.common.event.VoucherPurchaseCompletedEvent;
import com.pintoss.auth.core.order.domain.PurchaseResult;
import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import com.pintoss.auth.core.voucher.application.flow.external.VoucherEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class VoucherPurchaseUsecase {
    private final PurchaseApiClient purchaseApiClient;
    private final VoucherEventPublisher eventPublisher;

    public VoucherPurchaseUsecase(PurchaseApiClient purchaseApiClient, VoucherEventPublisher eventPublisher) {
        this.purchaseApiClient = purchaseApiClient;
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

        PurchaseResult result = purchaseApiClient.purchase(
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
