package com.pintoss.auth.core.voucher.application;

import com.pintoss.auth.core.support.event.VoucherPurchaseCompletedEvent;
import com.pintoss.auth.core.voucher.application.dto.PurchaseResult;
import com.pintoss.auth.core.voucher.application.dto.VoucherPurchaseCommand;
import com.pintoss.auth.core.voucher.application.flow.external.Purchaser;
import lombok.RequiredArgsConstructor;
import com.pintoss.auth.core.voucher.application.event.VoucherPurchaseCompletedEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherPurchaseUsecase {

    private final Purchaser purchaser;
    private final VoucherPurchaseCompletedEventPublisher eventPublisher;

    public void purchase(VoucherPurchaseCommand command) {

        PurchaseResult result = purchaser.purchase(command); // "1104501710200000"
        eventPublisher.publish(
            new VoucherPurchaseCompletedEvent(
                result.isSuccess(),
                command.getOrderNo(),
                command.getOrderItemId(),
                result.getPinNo(),
                result.getApprovalCode()
            )
        );
    }

}
