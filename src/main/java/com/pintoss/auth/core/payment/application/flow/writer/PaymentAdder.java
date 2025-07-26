package com.pintoss.auth.core.payment.application.flow.writer;

import com.pintoss.auth.core.payment.application.repository.PaymentRepository;
import com.pintoss.auth.core.payment.domain.PaymentDomain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentAdder {

    private final PaymentRepository paymentRepository;

    public PaymentDomain add(PaymentDomain payment){
        return paymentRepository.save(payment);
    }
}
