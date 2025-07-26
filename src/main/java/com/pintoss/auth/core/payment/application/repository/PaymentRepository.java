package com.pintoss.auth.core.payment.application.repository;

import com.pintoss.auth.core.payment.domain.PaymentDomain;

public interface PaymentRepository {

    PaymentDomain save(PaymentDomain paymentDomain);

    PaymentDomain findByOrderNo(String orderNo);

}
