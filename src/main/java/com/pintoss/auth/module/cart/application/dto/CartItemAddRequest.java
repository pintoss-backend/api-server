package com.pintoss.auth.module.cart.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemAddRequest {

    private Long productId;
    private int quantity;

}
