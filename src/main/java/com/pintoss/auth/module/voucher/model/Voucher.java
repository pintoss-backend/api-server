package com.pintoss.auth.module.voucher.model;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "voucher_issuer_id")
    private Long voucherIssuerId;

    private String name;

    private String issuerName;

    private Long price;

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

    public void validatePrice(Long price) {
        if (!this.price.equals(price)) {
            throw new BadRequestException(ErrorCode.VOUCHER_PRICE_MISMATCH);
        }
    }
}
