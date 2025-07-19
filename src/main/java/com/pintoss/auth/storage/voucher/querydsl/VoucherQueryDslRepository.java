package com.pintoss.auth.storage.voucher.querydsl;


import com.pintoss.auth.core.voucher.domain.Voucher;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pintoss.auth.storage.voucher.jpa.entity.QVoucherEntity.voucherEntity;
import static com.pintoss.auth.storage.voucher.jpa.entity.QVoucherIssuerEntity.voucherIssuerEntity;

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
