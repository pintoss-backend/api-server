package com.pintoss.auth.core.voucher.usecase;

import com.pintoss.auth.common.client.billgate.PurchaseApiClient;
import com.pintoss.auth.common.event.VoucherPurchaseCompletedEvent;
import com.pintoss.auth.core.order.integration.PurchaseResult;
import com.pintoss.auth.core.payment.application.PaymentMethodType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class VoucherPurchaseService {
    private final PurchaseApiClient purchaseApiClient;
    private final ApplicationEventPublisher eventPublisher;

    public VoucherPurchaseService(PurchaseApiClient purchaseApiClient, ApplicationEventPublisher eventPublisher) {
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

        eventPublisher.publishEvent(
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
