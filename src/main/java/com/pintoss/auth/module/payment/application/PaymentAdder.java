package com.pintoss.auth.module.payment.application;

import com.pintoss.auth.common.event.PaymentFailedEvent;
import com.pintoss.auth.common.event.PaymentSuccessedEvent;
import com.pintoss.auth.module.payment.domain.PaymentDomain;
import com.pintoss.auth.module.payment.domain.PaymentStatus;
import com.pintoss.auth.module.payment.store.PaymentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentAdder {

    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PaymentDomain add(PaymentDomain payment){
        PaymentEntity paymentEntity = PaymentEntity.of(payment);

        paymentRepository.save(paymentEntity);

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            eventPublisher.publishEvent(new PaymentSuccessedEvent(
                payment.getOrderNo(),
                payment.getTransactionId(),
                payment.getServiceId(),
                payment.getAuthAmount(),
                payment.getAuthDate(),
                payment.getPaymentMethodType()
            ));
        } else if (payment.getStatus() == PaymentStatus.FAILED) {
            eventPublisher.publishEvent(new PaymentFailedEvent(
                payment.getOrderNo()
            ));
        }
        return payment;
    }
}
