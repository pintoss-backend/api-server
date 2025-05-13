package com.pintoss.auth.module.cart.api.dto;

import com.pintoss.auth.module.cart.application.dto.AddCartItemCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemAddRequest {
    private Long productId;

    private int quantity;

    public AddCartItemCommand to() {
        return AddCartItemCommand.builder()
            .productId(productId)
            .quantity(quantity)
            .build();
    }
}
