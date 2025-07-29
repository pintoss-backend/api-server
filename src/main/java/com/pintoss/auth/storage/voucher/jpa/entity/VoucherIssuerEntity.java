package com.pintoss.auth.storage.voucher.jpa.entity;

import com.pintoss.auth.core.voucher.domain.VoucherIssuer;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoucherIssuerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String code;

    @Embedded
    @Valid
    @AttributeOverrides({
            @AttributeOverride(name = "cardDiscount", column = @Column(name = "cardDiscount", nullable = false)),
            @AttributeOverride(name = "phoneDiscount", column = @Column(name = "phoneDiscount", nullable = false))
    })
    private DiscountEmbeddable discountEmbeddable;

    @Embedded
    @Valid
    private ContactInfoEmbeddable contactInfoEmbeddable;

    @NotNull
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Size(max = 30)
    @NotNull
    @Column(length = 30, nullable = false)
    private String publisher;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String note;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String imageUrl;

    private String descriptionImageUrl;

    @Column(nullable = false, precision = 5, scale = 2) // ex) 999.99 까지 가능
    private BigDecimal fee; // 수수료

    public static VoucherIssuerEntity create(String name, String code, DiscountEmbeddable discountEmbeddable, ContactInfoEmbeddable contactInfoEmbeddable, String description, String publisher, String note, String imageUrl, String detailImageUrl, BigDecimal fee) {
        return VoucherIssuerEntity.builder()
                .name(name)
                .code(code)
                .discountEmbeddable(discountEmbeddable)
                .contactInfoEmbeddable(contactInfoEmbeddable)
                .description(description)
                .publisher(publisher)
                .note(note)
                .imageUrl(imageUrl)
                .descriptionImageUrl(detailImageUrl)
                .fee(fee)
                .build();
    }

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
                .descriptionImageUrl(voucherIssuer.getDescriptionImageUrl())
                .fee(voucherIssuer.getFee())
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
                this.descriptionImageUrl,
                this.fee
        );
    }
}
