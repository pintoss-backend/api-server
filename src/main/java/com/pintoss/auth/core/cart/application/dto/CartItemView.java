package com.pintoss.auth.core.cart.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemView {

    private Long id;
    private Long productId;
    private int quantity;
    private String name;
    private Long price;
    private String imageUrl;

}
