package com.pintoss.auth.core.cart.domain;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CartItemTest {

    @Test
    void whenIncreaseQuantity_thenQuantityIncreases() {
        // Given
        CartItem cartItem = CartItem.create(1L, 1L, 1L, 0, false);

        // When
        cartItem.increaseQuantity(10);

        // Then
        assertThat(cartItem.getQuantity()).isEqualTo(10);
    }

    @Test
    void whenCalculateQuantityWithPositiveValue_thenQuantityIncreases() {
        // Given
        CartItem cartItem = CartItem.create(1L, 1L, 1L, 5, false);

        // When
        cartItem.calculateQuantity(3);

        // Then
        assertThat(cartItem.getQuantity()).isEqualTo(8);
    }

    @Test
    void whenCalculateQuantityWithNegativeValue_thenQuantityDecreases() {
        // Given
        CartItem cartItem = CartItem.create(1L, 1L, 1L, 5, false);

        // When
        cartItem.calculateQuantity(-2);

        // Then
        assertThat(cartItem.getQuantity()).isEqualTo(3);
    }

    @Test
    void whenCalculateQuantityResultsInZero_thenItemIsMarkedAsDeleted() {
        // Given
        CartItem cartItem = CartItem.create(1L, 1L, 1L, 5, false);

        // When
        cartItem.calculateQuantity(-5);

        // Then
        assertThat(cartItem.getQuantity()).isEqualTo(0);
        assertThat(cartItem.isDeleted()).isTrue();
    }

    @Test
    void whenCalculateQuantityResultsInNegative_thenThrowsBadRequestException() {
        // Given
        CartItem cartItem = CartItem.create(1L, 1L, 1L, 5, false);

        // When & Then
        assertThatThrownBy(() -> cartItem.calculateQuantity(-6))
            .isInstanceOf(BadRequestException.class)
            .hasMessageContaining(ErrorCode.INVALID_CART_ITEM_QUANTITY.getMessage());
    }
}
