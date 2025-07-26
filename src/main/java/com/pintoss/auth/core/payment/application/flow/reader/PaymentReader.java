package com.pintoss.auth.core.payment.application.flow.reader;

import com.pintoss.auth.core.payment.application.repository.PaymentRepository;
import com.pintoss.auth.core.payment.domain.PaymentDomain;
import org.springframework.stereotype.Component;

@Component
public class PaymentReader {

    private final PaymentRepository paymentRepository;

    public PaymentReader(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentDomain getOrElseThrow(String orderNo) {
        return paymentRepository.findByOrderNo(orderNo);
    }
}
