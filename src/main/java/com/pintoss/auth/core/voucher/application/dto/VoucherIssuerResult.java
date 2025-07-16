package com.pintoss.auth.core.voucher.application.dto;

import com.pintoss.auth.storage.voucher.jpa.entity.ContactInfoEmbeddable;
import com.pintoss.auth.storage.voucher.jpa.entity.DiscountEmbeddable;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoucherIssuerResult {

    private Long id;
    private String name;
    private DiscountEmbeddable discountEmbeddable;
    private ContactInfoEmbeddable contactInfoEmbeddable;
    private String description;
    private String publisher;
    private String imageUrl;
    private String note;

    @QueryProjection
    public VoucherIssuerResult(Long id, String name, DiscountEmbeddable discountEmbeddable,
                               ContactInfoEmbeddable contactInfoEmbeddable, String description, String publisher, String imageUrl, String note) {
        this.id = id;
        this.name = name;
        this.discountEmbeddable = discountEmbeddable;
        this.contactInfoEmbeddable = contactInfoEmbeddable;
        this.description = description;
        this.publisher = publisher;
        this.imageUrl = imageUrl;
        this.note = note;
    }

}
