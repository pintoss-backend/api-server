package com.pintoss.auth.storage.voucher.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiscountEmbeddable {

    private BigDecimal cardDiscount;
    private BigDecimal phoneDiscount;

    public DiscountEmbeddable(BigDecimal cardDiscount, BigDecimal phoneDiscount) {
        this.cardDiscount = cardDiscount;
        this.phoneDiscount = phoneDiscount;
    }

}
