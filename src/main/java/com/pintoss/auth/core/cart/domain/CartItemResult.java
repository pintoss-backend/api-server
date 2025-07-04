package com.pintoss.auth.core.cart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResult {

    private Long id;
    private Long productId;
    private int quantity;
    private String name;
    private Long price;
    private String imageUrl;

}
