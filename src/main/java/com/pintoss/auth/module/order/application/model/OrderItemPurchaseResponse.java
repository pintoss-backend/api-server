package com.pintoss.auth.module.order.application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderItemPurchaseResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderItemId;

    private String approvalCode;

    private String remainPrice;

    private String itemName;

    private OrderItemPurchaseResponse(
        Long orderItemId,
        String approvalCode,
        String remainPrice,
        String itemName
    ) {
        this.orderItemId = orderItemId;
        this.approvalCode = approvalCode;
        this.remainPrice = remainPrice;
        this.itemName = itemName;
    }

    public static OrderItemPurchaseResponse create(
        Long orderItemId,
        String approvalCode,
        String remainPrice,
        String itemName
    ) {
        return new OrderItemPurchaseResponse(
            orderItemId,
            approvalCode,
            remainPrice,
            itemName
        );
    }
}
