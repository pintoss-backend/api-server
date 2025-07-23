package com.pintoss.auth.core.payment.application;

import com.pintoss.auth.core.order.application.flow.validator.OrderValidator;
import com.pintoss.auth.core.order.domain.Order;
import com.pintoss.auth.core.payment.application.dto.PaymentCreateCommand;
import com.pintoss.auth.core.payment.application.dto.PaymentCreateResult;
import com.pintoss.auth.core.payment.application.flow.writer.PaymentAdder;
import com.pintoss.auth.core.payment.domain.PaymentCreatedEvent;
import com.pintoss.auth.core.payment.domain.PaymentDomain;
import com.pintoss.auth.core.payment.domain.PaymentStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentCreateUsecase {

    @Value("${galaxia.service-id}")
    private String serviceId;
    private final OrderValidator orderValidator;
    private final PaymentAdder paymentAdder;
    private final ApplicationEventPublisher eventPublisher;

    public PaymentCreateUsecase(OrderValidator orderValidator, PaymentAdder paymentAdder, ApplicationEventPublisher eventPublisher) {
        this.orderValidator = orderValidator;
        this.paymentAdder = paymentAdder;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public PaymentCreateResult createPayment(PaymentCreateCommand command) {
        Order order = orderValidator.getOrThrowIfNotExists(command.getOrderNo());

        PaymentDomain payment = PaymentDomain.create(
                serviceId,
                order.getOrderNo(),
                order.getCreatedAt(),
                order.getTotalPrice(),
                PaymentStatus.PENDING,
                command.getPaymentMethod()
        );

        paymentAdder.add(payment);

        eventPublisher.publishEvent(
                new PaymentCreatedEvent(order.getOrderNo())
        );

        return PaymentCreateResult.from(payment);
    }

}
