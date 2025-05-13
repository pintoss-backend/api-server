package com.pintoss.auth.module.cart.api.dto;

import com.pintoss.auth.module.cart.application.model.CartItemResult;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemListResponse {

    private Long id;
    private Long productId;
    private int quantity;
    private String name;
    private Long price;
    private String imageUrl;

    public static CartItemListResponse of(CartItemResult result) {
            return new CartItemListResponse(
                result.getId(),
                result.getProductId(),
                result.getQuantity(),
                result.getName(),
                result.getPrice(),
                result.getImageUrl()
        ); }

}
