package com.pintoss.auth.core.voucher.model;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Discount {

    private BigDecimal cardDiscount;
    private BigDecimal phoneDiscount;

    public Discount(BigDecimal cardDiscount, BigDecimal phoneDiscount) {
        this.cardDiscount = cardDiscount;
        this.phoneDiscount = phoneDiscount;
    }

}
