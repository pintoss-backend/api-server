package com.pintoss.auth.module.voucher.external.persistence;

import static com.pintoss.auth.module.voucher.model.QVoucher.voucher;
import static com.pintoss.auth.module.voucher.model.QVoucherIssuer.voucherIssuer;

import com.pintoss.auth.module.voucher.model.Voucher;
import com.pintoss.auth.module.voucher.model.VoucherIssuer;
import com.pintoss.auth.module.voucher.usecase.dto.QVoucherIssuerResult;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerDetailResult;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerResult;
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
                    voucherIssuer.id,
                    voucherIssuer.name,
                    voucherIssuer.discount,
                    voucherIssuer.contactInfo,
                    voucherIssuer.description,
                    voucherIssuer.publisher,
                    voucherIssuer.imageUrl,
                    voucherIssuer.note
                )
            )
            .from(voucherIssuer)
            .fetch();
    }

    public Optional<VoucherIssuerDetailResult> fetchDetail(Long voucherIssuerId) {
        VoucherIssuer issuer = queryFactory.select(voucherIssuer)
            .from(voucherIssuer)
            .where(voucherIssuer.id.eq(voucherIssuerId))
            .fetchOne();

        if(issuer == null) {
            return Optional.empty();
        }

        List<Voucher> vouchers = queryFactory.selectFrom(voucher)
            .where(voucher.voucherIssuerId.eq(voucherIssuerId))
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
            .vouchers(vouchers.stream().map(v ->
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
