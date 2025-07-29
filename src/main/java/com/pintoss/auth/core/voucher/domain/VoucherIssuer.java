package com.pintoss.auth.core.voucher.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    private String descriptionImageUrl;

    private BigDecimal fee; // 수수료

    public static VoucherIssuer create(String name, String code, Discount discount, ContactInfo contactInfo, String description, String publisher,
                                       String note, String imageUrl, String detailImageUrl, BigDecimal fee) {
        return VoucherIssuer.builder()
                .name(name)
                .code(code)
                .discount(discount)
                .contactInfo(contactInfo)
                .description(description)
                .publisher(publisher)
                .note(note)
                .imageUrl(imageUrl)
                .descriptionImageUrl(detailImageUrl)
                .fee(fee)
                .build();
    }

}
