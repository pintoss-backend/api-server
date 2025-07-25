package com.pintoss.auth.core.voucher.domain;

import com.pintoss.auth.support.exception.BadRequestException;
import com.pintoss.auth.support.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Voucher {
    private Long id;

    private Long voucherIssuerId;

    private String name;

    private String issuerName;

    private Long price;

    private String productCode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static Voucher create(Long voucherIssuerId, String name, String issuerName, Long price) {
        return Voucher.builder()
            .voucherIssuerId(voucherIssuerId)
            .name(name)
            .issuerName(issuerName)
            .price(price)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public static Voucher create(Long id, Long voucherIssuerId, String name, String issuerName, Long price) {
        return Voucher.builder()
            .id(id)
            .voucherIssuerId(voucherIssuerId)
            .name(name)
            .issuerName(issuerName)
            .price(price)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public void validatePrice(Long price) {
        if (!this.price.equals(price)) {
            throw new BadRequestException(ErrorCode.VOUCHER_PRICE_MISMATCH);
        }
    }

}
