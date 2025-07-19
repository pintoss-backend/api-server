package com.pintoss.auth.core.payment.application;

import com.pintoss.auth.core.payment.domain.PaymentCompletedEvent;
import com.pintoss.auth.core.order.application.flow.validator.OrderValidator;
import com.pintoss.auth.core.payment.application.dto.PaymentApprovalRequest;
import com.pintoss.auth.core.payment.application.dto.PaymentApprovalResponse;
import com.pintoss.auth.core.payment.application.dto.PurchaseCommand;
import com.pintoss.auth.core.payment.application.flow.event.PaymentEventPublisher;
import com.pintoss.auth.core.payment.application.flow.external.PaymentApprover;
import com.pintoss.auth.core.payment.application.flow.writer.PaymentAdder;
import com.pintoss.auth.core.payment.domain.PaymentDomain;
import com.pintoss.auth.core.payment.domain.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PurchasePaymentUsecase {

    private final PaymentApprover paymentApprover;
    private final OrderValidator orderValidator;
    private final PaymentAdder paymentAdder;
    private final PaymentEventPublisher eventPublisher;

    @Transactional
    public void purchase(PurchaseCommand command) {
        orderValidator.getOrThrowIfNotExists(command.getOrderNo());

        PaymentApprovalResponse approvalResponse = paymentApprover.approval(
            new PaymentApprovalRequest(command.getServiceCode(), command.getOrderNo(), command.getMessage()));


        PaymentDomain payment = new PaymentDomain(
            approvalResponse.getIsSuccess(),
            command.getServiceId(),
            command.getServiceCode(),
            command.getOrderNo(),
            approvalResponse.getOrderDate(),
            approvalResponse.getTransactionId(),
            approvalResponse.getAuthAmount(),
            approvalResponse.getAuthDate(),
            approvalResponse.getPaymentMethodType(),
            approvalResponse.getDetailResponseMessage(),
            approvalResponse.getJson()
        );
        paymentAdder.add(payment);

        eventPublisher.publish(
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
    }
}
