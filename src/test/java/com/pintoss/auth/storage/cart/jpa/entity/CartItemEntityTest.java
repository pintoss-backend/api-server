package com.pintoss.auth.storage.cart.jpa.entity;

import com.pintoss.auth.core.cart.domain.CartItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CartItemEntityTest {

    @Test
    @DisplayName("도메인 객체로 변환 시 필드가 올바르게 초기화되어야 한다")
    void givenCartItemEntity_whenCreatedDomain_thenFieldsAreInitialized() {
        // Given
        CartItemEntity cartItemEntity = CartItemEntity.create(1L, 1L, 0);

        // When
        CartItem cartItem = cartItemEntity.toDomain();

        // Then
        assertThat(cartItem.getUserId()).isEqualTo(cartItemEntity.getUserId());
        assertThat(cartItem.getProductId()).isEqualTo(cartItemEntity.getProductId());
        assertThat(cartItem.getQuantity()).isEqualTo(cartItemEntity.getQuantity());
        assertThat(cartItem.isDeleted()).isEqualTo(cartItemEntity.isDeleted());
    }

    @Test
    @DisplayName("필드를 업데이트할 때, 해당 필드들이 올바르게 변경되어야 한다")
    void givenCartItemEntity_whenUpdate_thenFieldsAreUpdated() {
        // Given
        CartItemEntity cartItemEntity = CartItemEntity.create(1L, 1L, 0);

        // When
        cartItemEntity.update(5, true);

        // Then
        assertThat(cartItemEntity.getQuantity()).isEqualTo(5);
        assertThat(cartItemEntity.isDeleted()).isTrue();
    }
}
