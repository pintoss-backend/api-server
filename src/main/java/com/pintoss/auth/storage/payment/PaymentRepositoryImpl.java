package com.pintoss.auth.storage.payment;

import com.pintoss.auth.core.payment.application.repository.PaymentRepository;
import com.pintoss.auth.core.payment.domain.PaymentDomain;
import com.pintoss.auth.core.support.exception.CoreErrorCode;
import com.pintoss.auth.core.support.exception.CoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public PaymentDomain save(PaymentDomain paymentDomain) {
        PaymentEntity paymentEntity = PaymentEntity.from(paymentDomain);
        paymentJpaRepository.save(paymentEntity);
        paymentDomain.assignId(paymentEntity.getId());
        return paymentDomain;
    }

    @Override
    public PaymentDomain findByOrderNo(String orderNo) {
        PaymentEntity entity  = paymentJpaRepository.findByOrderNo(orderNo)
            .orElseThrow(() -> new CoreException(CoreErrorCode.PAYMENT_NOT_FOUND));
        return entity.toDomain();
    }
}
