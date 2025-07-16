package com.pintoss.auth.core.cart.domain;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    private Long id;

    private Long userId;

    private Long productId;

    private int quantity;

    private boolean isDeleted;

    public static CartItem create(Long id, Long userId, Long productId, int quantity, boolean isDeleted) {
        return CartItem.builder()
                .id(id)
                .userId(userId)
                .productId(productId)
                .quantity(quantity)
                .isDeleted(isDeleted)
                .build();
    }

    public static CartItem create(Long userId, Long productId, int quantity) {
        return CartItem.builder()
                .userId(userId)
                .productId(productId)
                .quantity(quantity)
                .build();
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void deleted() {
        this.isDeleted = true;
    }

    public void calculateQuantity(int quantity) {
        this.quantity += quantity;
        if (this.quantity < 0) {
            throw new BadRequestException(ErrorCode.INVALID_CART_ITEM_QUANTITY);
        }

        if (this.quantity == 0) {
            this.deleted();
        }
    }
}
