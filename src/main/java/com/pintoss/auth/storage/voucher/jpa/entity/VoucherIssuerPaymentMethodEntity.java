package com.pintoss.auth.storage.voucher.jpa.entity;

import com.pintoss.auth.core.voucher.domain.VoucherIssuerPaymentMethodType;
import com.pintoss.auth.core.voucher.domain.VoucherIssuerPaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "voucher_issuer_payment_method")
public class VoucherIssuerPaymentMethodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voucher_issuer_id", nullable = false)
    private Long voucherIssuerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_type", nullable = false)
    private VoucherIssuerPaymentMethodType paymentMethodType;

    @Column(name = "discount_rate", nullable = false)
    private BigDecimal discountRate;

    public static VoucherIssuerPaymentMethodEntity from(
        VoucherIssuerPaymentMethod domain
    ) {
        return VoucherIssuerPaymentMethodEntity.builder()
            .id(domain.getId())
            .voucherIssuerId(domain.getVoucherIssuerId())
            .paymentMethodType(domain.getPaymentMethod())
            .discountRate(domain.getDiscountRate())
            .build();
    }

    public VoucherIssuerPaymentMethod toDomain() {
        return new VoucherIssuerPaymentMethod(
            id,
            voucherIssuerId,
            paymentMethodType,
            discountRate
        );
    }
}
