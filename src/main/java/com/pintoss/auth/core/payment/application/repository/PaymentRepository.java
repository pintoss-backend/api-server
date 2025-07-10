package com.pintoss.auth.core.payment.application.repository;

import com.pintoss.auth.storage.payment.PaymentEntity;

public interface PaymentRepository {

    PaymentEntity save(PaymentEntity paymentEntity);
}
