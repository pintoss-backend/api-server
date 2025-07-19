package com.pintoss.auth.storage.voucher.jpa.entity;

import com.pintoss.auth.core.voucher.domain.Discount;
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

    public DiscountEmbeddable(Discount discount) {
        this.cardDiscount = discount.getCardDiscount();
        this.phoneDiscount = discount.getPhoneDiscount();
    }

    public Discount toDomain() {
        return new Discount(cardDiscount, phoneDiscount);
    }
}
