package com.pintoss.auth.module.voucher.usecase.dto;

import com.pintoss.auth.module.voucher.model.ContactInfo;
import com.pintoss.auth.module.voucher.model.Discount;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoucherIssuerResult {

    private Long id;
    private String name;
    private Discount discount;
    private ContactInfo contactInfo;
    private String description;
    private String publisher;
    private String imageUrl;
    private String note;

    @QueryProjection
    public VoucherIssuerResult(Long id, String name, Discount discount,
        ContactInfo contactInfo, String description, String publisher, String imageUrl, String note) {
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.contactInfo = contactInfo;
        this.description = description;
        this.publisher = publisher;
        this.imageUrl = imageUrl;
        this.note = note;
    }

}
