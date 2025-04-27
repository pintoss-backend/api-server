package com.pintoss.auth.module.voucher.usecase.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pintoss.auth.module.voucher.model.ContactInfo;
import com.pintoss.auth.module.voucher.model.Discount;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class VoucherIssuerDetailResult {

    private Long id;
    private String name;
    private String description;
    private Discount discount;
    private ContactInfo contactInfo;
    private String publisher;
    private String note;
    private String imageUrl;
    private List<VoucherInfo> vouchers;
    private BigDecimal fee;

    @Getter
    @Builder
    public static class VoucherInfo {
        private Long id;
        private String name;
        private String issuerName;
        private Long price;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
    }

}
