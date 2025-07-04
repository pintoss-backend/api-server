package com.pintoss.auth.storage.voucher;

import com.pintoss.auth.core.voucher.domain.ContactInfo;
import com.pintoss.auth.core.voucher.domain.Discount;
import com.pintoss.auth.core.voucher.domain.VoucherIssuer;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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
    private Discount discount;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "homePage", column = @Column(name = "homePage", nullable = false)),
        @AttributeOverride(name = "csCenter", column = @Column(name = "csCenter", nullable = false)),
    })
    private ContactInfo contactInfo;

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

    private VoucherIssuerEntity(String name, String code, Discount discount, ContactInfo contactInfo, String description, String publisher, String note, String imageUrl, BigDecimal fee) {
        this.name = name;
        this.code = code;
        this.discount = discount;
        this.contactInfo = contactInfo;
        this.description = description;
        this.publisher = publisher;
        this.note = note;
        this.imageUrl = imageUrl;
        this.fee = fee;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static VoucherIssuerEntity create(String name, String code, Discount discount, ContactInfo contactInfo, String description, String publisher,
        String note, String imageUrl, BigDecimal fee) {
        return new VoucherIssuerEntity(
            name,
            code,
            discount,
            contactInfo,
            description,
            publisher,
            note,
            imageUrl,
            fee
        );
    }

    public static VoucherIssuerEntity from(VoucherIssuer voucherIssuer) {
        return new VoucherIssuerEntity(
            voucherIssuer.getName(),
            voucherIssuer.getCode(),
            voucherIssuer.getDiscount(),
            voucherIssuer.getContactInfo(),
            voucherIssuer.getDescription(),
            voucherIssuer.getPublisher(),
            voucherIssuer.getNote(),
            voucherIssuer.getImageUrl(),
            voucherIssuer.getFee()
        );
    }
}
