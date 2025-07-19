package com.pintoss.auth.storage.voucher.querydsl;


import com.pintoss.auth.core.voucher.application.dto.QVoucherIssuerResult;
import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerDetailResult;
import com.pintoss.auth.core.voucher.application.dto.VoucherIssuerResult;
import com.pintoss.auth.storage.voucher.jpa.entity.VoucherEntity;
import com.pintoss.auth.storage.voucher.jpa.entity.VoucherIssuerEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.pintoss.auth.storage.voucher.jpa.entity.QVoucherEntity.voucherEntity;
import static com.pintoss.auth.storage.voucher.jpa.entity.QVoucherIssuerEntity.voucherIssuerEntity;


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
                    voucherIssuerEntity.discountEmbeddable,
                    voucherIssuerEntity.contactInfoEmbeddable,
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
            .discountEmbeddable(issuer.getDiscountEmbeddable())
            .contactInfoEmbeddable(issuer.getContactInfoEmbeddable())
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
