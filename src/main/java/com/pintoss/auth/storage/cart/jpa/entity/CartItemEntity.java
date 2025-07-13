package com.pintoss.auth.storage.cart.jpa.entity;

import com.pintoss.auth.core.cart.domain.CartItem;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "cart_item")
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private Long productId;

    private int quantity;

    private boolean isDeleted;

    public static CartItemEntity create(Long userId, Long productId, int quantity) {
        return CartItemEntity.builder()
                .userId(userId)
                .productId(productId)
                .quantity(quantity)
                .build();
    }

    public CartItem toDomain() {
        return CartItem.create(id, userId, productId, quantity, isDeleted);
    }

    public void update(int quantity, boolean isDeleted) {
        this.quantity = quantity;
        this.isDeleted = isDeleted;
    }
}
