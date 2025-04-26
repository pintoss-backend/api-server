package com.pintoss.auth.module.voucher.usecase.dto;

import com.pintoss.auth.module.voucher.execution.domain.ContactInfo;
import com.pintoss.auth.module.voucher.execution.domain.Discount;
import com.pintoss.auth.module.voucher.execution.domain.VoucherIssuerDetailResult.VoucherInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoucherIssuerDetailResponse {

    private Long id;
    private String name;
    private String description;
    private Discount discount;
    private ContactInfo contactInfo;
    private String publisher;
    private String note;
    private String imageUrl;
    private List<VoucherInfo> vouchers;

}
