package com.pintoss.auth.core.payment.store;

import com.pintoss.auth.core.payment.application.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public PaymentEntity save(PaymentEntity paymentEntity) {
        paymentJpaRepository.save(paymentEntity);
        // TODO : PaymentEntity 도메인 객체를 PaymentEntity 객체로 변환해서 데이터베이스에 저장한다.
        return paymentEntity;
    }
}
