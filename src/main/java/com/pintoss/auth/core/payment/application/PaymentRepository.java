package com.pintoss.auth.core.payment.application;

import com.pintoss.auth.core.payment.store.PaymentEntity;

public interface PaymentRepository {

    PaymentEntity save(PaymentEntity paymentEntity);
}
