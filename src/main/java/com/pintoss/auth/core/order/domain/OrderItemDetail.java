package com.pintoss.auth.core.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDetail {

    private String voucherIssuerName;
    private String voucherName;
    private Long price;
    private String pinNum;
    private OrderItemStatus status;

}
