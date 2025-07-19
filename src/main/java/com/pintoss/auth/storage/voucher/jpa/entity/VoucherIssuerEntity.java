package com.pintoss.auth.storage.voucher.jpa.entity;

import com.pintoss.auth.core.voucher.domain.VoucherIssuer;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoucherIssuerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "cardDiscount", column = @Column(name = "cardDiscount", nullable = false)),
        @AttributeOverride(name = "phoneDiscount", column = @Column(name = "phoneDiscount", nullable = false))
    })
    private DiscountEmbeddable discountEmbeddable;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "homePage", column = @Column(name = "homePage", nullable = false)),
        @AttributeOverride(name = "csCenter", column = @Column(name = "csCenter", nullable = false)),
    })
    private ContactInfoEmbeddable contactInfoEmbeddable;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(length = 30, nullable = false)
    private String publisher;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String note;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String imageUrl;

    @Column(nullable = false, precision = 5, scale = 2) // ex) 999.99 까지 가능
    private BigDecimal fee; // 수수료

    public static VoucherIssuerEntity from(VoucherIssuer voucherIssuer) {
        return VoucherIssuerEntity.builder()
                .name(voucherIssuer.getName())
                .code(voucherIssuer.getCode())
                .discountEmbeddable(new DiscountEmbeddable(voucherIssuer.getDiscount()))
                .contactInfoEmbeddable(new ContactInfoEmbeddable(voucherIssuer.getContactInfo()))
                .description(voucherIssuer.getDescription())
                .publisher(voucherIssuer.getPublisher())
                .note(voucherIssuer.getNote())
                .imageUrl(voucherIssuer.getImageUrl())
                .fee(voucherIssuer.getFee())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public VoucherIssuer toDomain() {
        return VoucherIssuer.create(
                this.name,
                this.code,
                this.discountEmbeddable.toDomain(),
                this.contactInfoEmbeddable.toDomain(),
                this.description,
                this.publisher,
                this.note,
                this.imageUrl,
                this.fee
        );
    }
}
