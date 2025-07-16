package com.pintoss.auth.storage.cart.jpa;

import com.pintoss.auth.storage.cart.jpa.entity.CartItemEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class CartItemJpaRepositoryTest {

    @Autowired
    private CartItemJpaRepository cartItemJpaRepository;

    @Test
    @DisplayName("저장된 장바구니 아이템을 조회할 수 있어야 한다")
    void givenCartItem_whenFindByUserId_thenReturnCartItems() {
        // Given
        cartItemJpaRepository.saveAll(List.of(
                CartItemEntity.create(1L, 1L, 1),
                CartItemEntity.create(1L, 2L, 2)
        ));

        // When
        List<CartItemEntity> cartItems = cartItemJpaRepository.findByUserId(1L);

        // Then
        assertThat(cartItems).hasSize(2);
        assertThat(cartItems.get(0).getUserId()).isEqualTo(1L);
        assertThat(cartItems.get(0).getProductId()).isEqualTo(1L);
        assertThat(cartItems.get(1).getUserId()).isEqualTo(1L);
        assertThat(cartItems.get(1).getProductId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("장바구니 아이템이 없을 때 빈 리스트를 반환해야 한다")
    void givenNoCartItems_whenFindByUserId_thenReturnEmptyList() {
        // Given

        // When
        List<CartItemEntity> cartItems = cartItemJpaRepository.findByUserId(1L);

        // Then
        assertThat(cartItems).isEmpty();
    }

    @Test
    @DisplayName("장바구니 아이템의 수량이 정상적으로 변경되어야 한다")
    void givenCartItem_whenUpdateQuantity_thenQuantityShouldBeUpdated() {
        // Given
        CartItemEntity cartItem = CartItemEntity.create(1L, 1L, 1);
        cartItemJpaRepository.save(cartItem);

        int newQuantity = 2;

        // When
        cartItem.update(newQuantity, cartItem.isDeleted());

        // Then
        CartItemEntity updatedCartItem = cartItemJpaRepository.findById(cartItem.getId()).orElseThrow();
        assertThat(updatedCartItem.getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 아이템의 삭제 상태가 정상적으로 변경되어야 한다")
    void givenCartItem_whenUpdateDeleted_thenDeletedStatusShouldBeUpdated() {
        // Given
        CartItemEntity cartItem = CartItemEntity.create(1L, 1L, 1);
        cartItemJpaRepository.save(cartItem);

        boolean newDeletedStatus = true;

        // When
        cartItem.update(cartItem.getQuantity(), newDeletedStatus);

        // Then
        CartItemEntity updatedCartItem = cartItemJpaRepository.findById(cartItem.getId()).orElseThrow();
        assertThat(updatedCartItem.isDeleted()).isTrue();
    }
}
