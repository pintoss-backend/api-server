package com.pintoss.auth.module.payment.application;

import com.pintoss.auth.module.payment.store.PaymentEntity;

public interface PaymentRepository {

    PaymentEntity save(PaymentEntity paymentEntity);
}
