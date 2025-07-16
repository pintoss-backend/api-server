package com.pintoss.auth.core.voucher.domain;

import com.pintoss.auth.storage.voucher.jpa.entity.ContactInfoEmbeddable;
import com.pintoss.auth.storage.voucher.jpa.entity.DiscountEmbeddable;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class VoucherIssuer {
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

    private VoucherIssuer(String name, String code, DiscountEmbeddable discountEmbeddable, ContactInfoEmbeddable contactInfoEmbeddable, String description, String publisher, String note, String imageUrl, BigDecimal fee) {
        this.name = name;
        this.code = code;
        this.discountEmbeddable = discountEmbeddable;
        this.contactInfoEmbeddable = contactInfoEmbeddable;
        this.description = description;
        this.publisher = publisher;
        this.note = note;
        this.imageUrl = imageUrl;
        this.fee = fee;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static VoucherIssuer create(String name, String code, DiscountEmbeddable discountEmbeddable, ContactInfoEmbeddable contactInfoEmbeddable, String description, String publisher,
                                       String note, String imageUrl, BigDecimal fee) {
        return new VoucherIssuer(
            name,
            code,
                discountEmbeddable,
                contactInfoEmbeddable,
            description,
            publisher,
            note,
            imageUrl,
            fee
        );
    }

}
