package com.pintoss.auth.api.order.dto;

import com.pintoss.auth.core.order.application.dto.OrderItemDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OrderItemDetailResponse {

    private String voucherIssuerName;
    private String voucherName;
    private Long price;
    private String pinNum;
    private String status;

    public OrderItemDetailResponse(String voucherIssuerName, String voucherName, Long price, String pinNum, String status) {
        this.voucherIssuerName = voucherIssuerName;
        this.voucherName = voucherName;
        this.price = price;
        this.pinNum = pinNum;
        this.status = status;
    }

    public static OrderItemDetailResponse from(OrderItemDetail response) {
        return new OrderItemDetailResponse(
                response.getVoucherIssuerName(),
                response.getVoucherName(),
                response.getPrice(),
                response.getPinNum(),
                response.getStatus().name()
        );
    }
}
