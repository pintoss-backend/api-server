package com.pintoss.auth.core.cart.application.model;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private Long productId;

    private int quantity;

    private boolean isDeleted;

    public CartItem(Long userId, Long productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.isDeleted = false;
    }
    public static CartItem create(Long memberId, Long productId, int quantity) {
        return new CartItem(memberId, productId, quantity);
    }


    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void deleted() {
        this.isDeleted = true;
    }

    public void calculateQuantity(int quantity) {
        this.quantity += quantity;
        if(this.quantity < 0) {
            throw new BadRequestException(ErrorCode.INVALID_CART_ITEM_QUANTITY);
        } else if (this.quantity == 0) {
            this.deleted();
        }
    }
}
