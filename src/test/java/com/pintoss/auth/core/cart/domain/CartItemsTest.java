package com.pintoss.auth.core.cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class CartItemsTest {

    @Test
    @DisplayName("기존에 없던 아이템이 장바구니에 추가되면 새롭게 추가되어야 한다")
    void givenCartItems_whenAddNewCartItems_thenCartItemsShouldContainNewItems() {
        // Given
        Long userId = 1L;
        CartItems cartItems = new CartItems(List.of(
                CartItem.create(1L, userId, 1L, 1, false),
                CartItem.create(2L, userId, 2L, 2, false)
        ));

        Map<Long, Integer> productQuantityMap = new LinkedHashMap<>();
        productQuantityMap.put(3L, 3);

        // When
        CartItems newCartItems = cartItems.addCartItems(1L, productQuantityMap);

        // Then
        assertThat(newCartItems.getCartItems()).hasSize(3);
        assertThat(newCartItems.getCartItems())
                .extracting(CartItem::getProductId, CartItem::getQuantity)
                .containsExactly(
                        tuple(1L, 1),
                        tuple(2L, 2),
                        tuple(3L, 3)
                );
    }

    @Test
    @DisplayName("장바구니가 비었을 때 새로운 아이템을 추가하면 장바구니에 아이템이 추가되어야 한다")
    void givenEmptyCartItems_whenAddNewCartItems_thenCartItemsShouldContainNewItems() {
        // Given
        Long userId = 1L;
        CartItems cartItems = new CartItems(List.of());

        Map<Long, Integer> productQuantityMap = new LinkedHashMap<>();
        productQuantityMap.put(2L, 3);
        productQuantityMap.put(3L, 4);

        // When
        CartItems newCartItems = cartItems.addCartItems(userId, productQuantityMap);

        // Then
        assertThat(newCartItems.getCartItems()).hasSize(2);
        assertThat(newCartItems.getCartItems())
                .extracting(CartItem::getProductId, CartItem::getQuantity)
                .containsExactly(
                        tuple(2L, 3),
                        tuple(3L, 4)
                );
    }

    @Test
    @DisplayName("기존 장바구니에 있는 아이템의 수량만 업데이트되어야 한다")
    void givenCartItemsWithExistingProducts_whenAddNewCartItems_thenQuantitiesShouldBeUpdated() {
        // Given
        Long userId = 1L;
        CartItems cartItems = new CartItems(List.of(
                CartItem.create(1L, userId, 1L, 2, false),
                CartItem.create(2L, userId, 2L, 3, false)
        ));

        Map<Long, Integer> productQuantityMap = new LinkedHashMap<>();
        productQuantityMap.put(2L, 3);
        productQuantityMap.put(3L, 4);

        // When
        CartItems newCartItems = cartItems.addCartItems(userId, productQuantityMap);

        // Then
        assertThat(newCartItems.getCartItems()).hasSize(3);
        assertThat(newCartItems.getCartItems())
                .extracting(CartItem::getProductId, CartItem::getQuantity)
                .containsExactly(
                        tuple(1L, 2), // 기존 아이템
                        tuple(2L, 6), // 기존 아이템의 수량 업데이트
                        tuple(3L, 4)  // 새로 추가된 아이템
                );
    }

    @Test
    @DisplayName("기존 장바구니 아이템의 수량은 추가되고 새로운 아이템도 추가되어야 한다")
    void givenCartItemsWithNoExistingProducts_whenAddNewCartItems_thenExistingQuantityIncreaseAndNewQuantityAdded() {
        // Given
        Long userId = 1L;
        CartItem item1 = CartItem.create(1L, userId, 1L, 2, false);
        CartItems cartItems = new CartItems(List.of(item1));

        Map<Long, Integer> productQuantityMap = new LinkedHashMap<>();
        productQuantityMap.put(1L, 3);
        productQuantityMap.put(2L, 4);


        // When
        CartItems newCartItems = cartItems.addCartItems(userId, productQuantityMap);

        // Then
        assertThat(newCartItems.getCartItems()).hasSize(2);
        assertThat(newCartItems.getCartItems())
                .extracting(CartItem::getProductId, CartItem::getQuantity)
                .containsExactly(
                        tuple(1L, 5), // 기존 아이템의 수량이 증가
                        tuple(2L, 4)  // 새로 추가된 아이템
                );
    }
}
