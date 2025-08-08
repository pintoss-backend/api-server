package com.pintoss.auth.storage.voucher.querydsl;


import static com.pintoss.auth.storage.voucher.jpa.entity.QVoucherIssuerPaymentMethodEntity.voucherIssuerPaymentMethodEntity;

import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerPaymentMethodResult;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VoucherIssuerPaymentMethodQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<VoucherIssuerPaymentMethodResult> fetchVoucherIssuerPaymentDiscountResult() {
        return queryFactory
            .select(
                Projections.constructor(
                    VoucherIssuerPaymentMethodResult.class,
                    voucherIssuerPaymentMethodEntity.voucherIssuerId,
                    voucherIssuerPaymentMethodEntity.paymentMethodType,
                    voucherIssuerPaymentMethodEntity.discountRate))
            .from(voucherIssuerPaymentMethodEntity)
            .fetch();
    }

}
