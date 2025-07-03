package com.pintoss.auth.core.voucher.model;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Voucher {
    private Long id;

    private Long voucherIssuerId;

    private String name;

    private String issuerName;

    private Long price;

    private String productCode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Voucher(Long voucherIssuerId, String name, String issuerName, Long price) {
        this.voucherIssuerId = voucherIssuerId;
        this.name = name;
        this.issuerName = issuerName;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static Voucher create(Long voucherIssuerId, String name, String issuerName, Long price) {
        return new Voucher(voucherIssuerId, name, issuerName, price);
    }
    public Voucher(Long id, Long voucherIssuerId, String name, String issuerName, Long price, String productCode, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.voucherIssuerId = voucherIssuerId;
        this.name = name;
        this.issuerName = issuerName;
        this.price = price;
        this.productCode = productCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void validatePrice(Long price) {
        if (!this.price.equals(price)) {
            throw new BadRequestException(ErrorCode.VOUCHER_PRICE_MISMATCH);
        }
    }

}
