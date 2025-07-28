package com.pintoss.auth.core.payment.application;

import com.pintoss.auth.core.payment.application.dto.PaymentApprovalRequest;
import com.pintoss.auth.core.payment.application.dto.PaymentApprovalResponse;
import com.pintoss.auth.core.payment.application.dto.PurchaseCommand;
import com.pintoss.auth.core.payment.application.event.OrderCanceledEventPublisher;
import com.pintoss.auth.core.payment.application.event.PaymentCompletedEventPublisher;
import com.pintoss.auth.core.payment.application.flow.external.PaymentApprover;
import com.pintoss.auth.core.payment.application.flow.reader.PaymentReader;
import com.pintoss.auth.core.payment.application.flow.writer.PaymentAdder;
import com.pintoss.auth.core.support.event.PaymentCompletedEvent;
import com.pintoss.auth.core.payment.domain.PaymentDomain;
import com.pintoss.auth.core.payment.domain.PaymentStatus;
import com.pintoss.auth.core.support.event.OrderCanceledEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PurchasePaymentUsecase {

    private final PaymentReader paymentReader;
    private final PaymentApprover paymentApprover;
    private final PaymentAdder paymentAdder;
    private final PaymentCompletedEventPublisher paymentCompletedEventPublisher;
    private final OrderCanceledEventPublisher orderCanceledEventPublisher;

    @Transactional
    public void purchase(PurchaseCommand command) {
        PaymentDomain payment = paymentReader.getOrElseThrow(command.getOrderNo());

        if (!isCanceled(command.getResponseCode())) {
            PaymentApprovalResponse approvalResponse = paymentApprover.approval(
                new PaymentApprovalRequest(command.getServiceCode(), command.getOrderNo(), command.getMessage()));

            payment.approve(
                approvalResponse.getIsSuccess(),
                approvalResponse.getTransactionId(),
                approvalResponse.getAuthDate(),
                approvalResponse.getDetailResponseMessage(),
                approvalResponse.getJson()
            );

            paymentCompletedEventPublisher.publish(
                new PaymentCompletedEvent(
                    payment.getStatus() == PaymentStatus.SUCCESS ? true : false,
                    payment.getId(),
                    payment.getOrderNo(),
                    payment.getTransactionId(),
                    payment.getServiceId(),
                    payment.getAuthAmount(),
                    payment.getAuthDate(),
                    payment.getPaymentMethodType()
                )
            );
        } else {
            payment.cancel();
            orderCanceledEventPublisher.publish(new OrderCanceledEvent(payment.getOrderNo()));
        }
        paymentAdder.add(payment);
    }

    private boolean isCanceled(String responseCode) {
        return responseCode.equals("1111");
    }
}
