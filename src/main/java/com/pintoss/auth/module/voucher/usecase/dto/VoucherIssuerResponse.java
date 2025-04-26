package com.pintoss.auth.module.voucher.usecase.dto;

import com.pintoss.auth.module.voucher.execution.domain.ContactInfo;
import com.pintoss.auth.module.voucher.execution.domain.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class VoucherIssuerResponse {

    private Long id;
    private String name;
    private Discount discount;
    private ContactInfo contactInfo;
    private String description;
    private String publisher;
    private String imageUrl;
    private String note;

}
