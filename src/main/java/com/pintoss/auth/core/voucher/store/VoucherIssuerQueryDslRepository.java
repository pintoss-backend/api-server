package com.pintoss.auth.core.voucher.store;


import static com.pintoss.auth.core.voucher.store.QVoucherEntity.voucherEntity;
import static com.pintoss.auth.core.voucher.store.QVoucherIssuerEntity.voucherIssuerEntity;

import com.pintoss.auth.core.voucher.usecase.dto.QVoucherIssuerResult;
import com.pintoss.auth.core.voucher.usecase.dto.VoucherIssuerDetailResult;
import com.pintoss.auth.core.voucher.usecase.dto.VoucherIssuerResult;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VoucherIssuerQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<VoucherIssuerResult> fetchSummaryList() {
        return queryFactory
            .select(
                new QVoucherIssuerResult(
                    voucherIssuerEntity.id,
                    voucherIssuerEntity.name,
                    voucherIssuerEntity.discount,
                    voucherIssuerEntity.contactInfo,
                    voucherIssuerEntity.description,
                    voucherIssuerEntity.publisher,
                    voucherIssuerEntity.imageUrl,
                    voucherIssuerEntity.note
                )
            )
            .from(voucherIssuerEntity)
            .fetch();
    }

    public Optional<VoucherIssuerDetailResult> fetchDetail(Long voucherIssuerId) {
        VoucherIssuerEntity issuer = queryFactory.select(voucherIssuerEntity)
            .from(voucherIssuerEntity)
            .where(voucherIssuerEntity.id.eq(voucherIssuerId))
            .fetchOne();

        if(issuer == null) {
            return Optional.empty();
        }

        List<VoucherEntity> voucherEntities = queryFactory.selectFrom(voucherEntity)
            .where(voucherEntity.voucherIssuerId.eq(voucherIssuerId))
            .fetch();

        VoucherIssuerDetailResult result = VoucherIssuerDetailResult.builder()
            .id(issuer.getId())
            .name(issuer.getName())
            .description(issuer.getDescription())
            .discount(issuer.getDiscount())
            .contactInfo(issuer.getContactInfo())
            .publisher(issuer.getPublisher())
            .note(issuer.getNote())
            .imageUrl(issuer.getImageUrl())
            .fee(issuer.getFee())
            .vouchers(voucherEntities.stream().map(v ->
                VoucherIssuerDetailResult.VoucherInfo.builder()
                    .id(v.getId())
                    .name(v.getName())
                    .issuerName(v.getIssuerName())
                    .price(v.getPrice())
                    .createdAt(v.getCreatedAt())
                    .build()
            ).toList())
            .build();
        return Optional.of(result);
    }
}
