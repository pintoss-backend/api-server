package com.pintoss.auth.core.voucher.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Discount {

    private BigDecimal cardDiscount;
    private BigDecimal phoneDiscount;
}
