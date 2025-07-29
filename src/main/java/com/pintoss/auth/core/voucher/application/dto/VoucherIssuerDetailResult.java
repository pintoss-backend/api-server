package com.pintoss.auth.core.voucher.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pintoss.auth.core.voucher.domain.ContactInfo;
import com.pintoss.auth.core.voucher.domain.Discount;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private String descriptionImageUrl;
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
