package com.pintoss.auth.core.voucher.domain;

import jakarta.persistence.Column;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class VoucherIssuer {

    private Long id;
    private String name;
    private String code;

    private Discount discount;

    private ContactInfo contactInfo;

    private String description;

    private String publisher;

    private String note;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String imageUrl;

    @Column(nullable = false, precision = 5, scale = 2) // ex) 999.99 까지 가능
    private BigDecimal fee; // 수수료

    private VoucherIssuer(String name, String code, Discount discount, ContactInfo contactInfo, String description, String publisher, String note, String imageUrl, BigDecimal fee) {
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

    public static VoucherIssuer create(String name, String code, Discount discount, ContactInfo contactInfo, String description, String publisher,
                                       String note, String imageUrl, BigDecimal fee) {
        return new VoucherIssuer(
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

}
