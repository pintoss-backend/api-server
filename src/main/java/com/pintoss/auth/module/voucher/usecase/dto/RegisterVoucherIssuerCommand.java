package com.pintoss.auth.module.voucher.usecase.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterVoucherIssuerCommand {

    private String name;
    private String code;
    private BigDecimal cardDiscount;
    private BigDecimal phoneDiscount;
    private String homePage;
    private String csCenter;
    private String description;
    private String publisher;
    private String imageUrl;
    private String note;
    private List<RegisterVoucherCommand> vouchers;

    @Data
    @Builder
    public static class RegisterVoucherCommand {
        private String name;
        private Long price;
        private int quantity;

    }
}
