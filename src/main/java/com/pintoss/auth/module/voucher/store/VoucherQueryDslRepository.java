package com.pintoss.auth.module.voucher.store;


import static com.pintoss.auth.module.voucher.store.QVoucherEntity.voucherEntity;
import static com.pintoss.auth.module.voucher.store.QVoucherIssuerEntity.voucherIssuerEntity;

import com.pintoss.auth.module.voucher.model.Voucher;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VoucherQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<Voucher> findAllByIds(List<Long> voucherIds) {
        return queryFactory.select(
                Projections.constructor(Voucher.class,
                    voucherEntity.id,
                    voucherIssuerEntity.id,
                    voucherEntity.name,
                    voucherEntity.issuerName,
                    voucherEntity.price,
                    voucherIssuerEntity.code.as("productCode"),
                    voucherEntity.createdAt,
                    voucherEntity.updatedAt
                ))
            .from(voucherEntity)
            .join(voucherIssuerEntity).on(voucherEntity.voucherIssuerId.eq(voucherIssuerEntity.id))
            .where(voucherEntity.id.in(voucherIds))
            .fetch();
    }
}
