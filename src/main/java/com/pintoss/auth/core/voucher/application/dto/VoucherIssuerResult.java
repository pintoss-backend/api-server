package com.pintoss.auth.core.voucher.application.dto;

import com.pintoss.auth.core.voucher.domain.ContactInfo;
import com.pintoss.auth.core.voucher.domain.Discount;
import com.pintoss.auth.storage.voucher.jpa.entity.VoucherIssuerEntity;
import lombok.*;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static VoucherIssuerResult create(VoucherIssuerEntity voucher) {
        return VoucherIssuerResult.builder()
                .id(voucher.getId())
                .name(voucher.getName())
                .discount(voucher.getDiscountEmbeddable().toDomain())
                .contactInfo(voucher.getContactInfoEmbeddable().toDomain())
                .description(voucher.getDescription())
                .publisher(voucher.getPublisher())
                .imageUrl(voucher.getImageUrl())
                .note(voucher.getNote())
                .build();
    }

//    @QueryProjection
//    public VoucherIssuerResult(Long id, String name, DiscountEmbeddable discount,
//                               ContactInfoEmbeddable contactInfo, String description, String publisher, String imageUrl, String note) {
//        this.id = id;
//        this.name = name;
//        this.discountEmbeddable = discount;
//        this.contactInfoEmbeddable = contactInfo;
//        this.description = description;
//        this.publisher = publisher;
//        this.imageUrl = imageUrl;
//        this.note = note;
//    }

}
