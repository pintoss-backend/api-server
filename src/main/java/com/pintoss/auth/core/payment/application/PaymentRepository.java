package com.pintoss.auth.core.payment.application;

import com.pintoss.auth.storage.payment.PaymentEntity;

public interface PaymentRepository {

    PaymentEntity save(PaymentEntity paymentEntity);
}
