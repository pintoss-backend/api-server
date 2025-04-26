package com.pintoss.auth.module.voucher.external.persistence;

import static com.pintoss.auth.module.voucher.execution.domain.QVoucher.voucher;
import static com.pintoss.auth.module.voucher.execution.domain.QVoucherIssuer.voucherIssuer;

import com.pintoss.auth.module.voucher.execution.domain.Voucher;
import com.pintoss.auth.module.voucher.execution.domain.VoucherIssuer;
import com.pintoss.auth.module.voucher.usecase.dto.QVoucherIssuerResponse;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerDetailResponse;
import com.pintoss.auth.module.voucher.usecase.dto.VoucherIssuerResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VoucherIssuerQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<VoucherIssuerResponse> fetchSummaryList() {
        return queryFactory
            .select(
                new QVoucherIssuerResponse(
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

    public VoucherIssuerDetailResponse fetchDetail(Long voucherIssuerId) {
        VoucherIssuer issuer = queryFactory.select(voucherIssuer)
            .from(voucherIssuer)
            .where(voucherIssuer.id.eq(voucherIssuerId))
            .fetchOne();

        List<Voucher> vouchers = queryFactory.selectFrom(voucher)
            .where(voucher.voucherIssuerId.eq(voucherIssuerId))
            .fetch();

        return VoucherIssuerDetailResponse.builder()
            .id(issuer.getId())
            .name(issuer.getName())
            .description(issuer.getDescription())
            .discount(issuer.getDiscount())
            .contactInfo(issuer.getContactInfo())
            .publisher(issuer.getPublisher())
            .note(issuer.getNote())
            .imageUrl(issuer.getImageUrl())
            .vouchers(vouchers.stream().map(v ->
                VoucherIssuerDetailResponse.VoucherInfo.builder()
                    .id(v.getId())
                    .name(v.getName())
                    .issuerName(v.getIssuerName())
                    .price(v.getPrice())
                    .createdAt(v.getCreatedAt())
                    .build()
            ).toList())
            .build();
    }
}
