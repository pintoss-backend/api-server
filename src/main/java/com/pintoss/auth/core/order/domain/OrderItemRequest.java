package com.pintoss.auth.core.order.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    @NotNull(message = "주문 상품은 필수 값 입니다.")
    private Long voucherId;
    @NotNull(message = "가격은 필수 값 입니다.")
    private Long price;
    @NotNull(message = "주문 수량은 필수 값 입니다.")
    private Integer quantity;
}
