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
    @DisplayName("도메인 객체를 엔티티로 변환할 때 필드가 올바르게 매핑되어야 한다")
    void givenCartItem_whenCreatedEntity_thenFieldsAreMapped() {
        // Given
        CartItem cartItem = CartItem.create(1L, 2L, 3L, 4, false);

        // When
        CartItemEntity cartItemEntity = CartItemEntity.from(cartItem);

        // Then
        assertThat(cartItemEntity).extracting( "id", "userId", "productId", "quantity", "deleted")
                .containsExactly(
                        cartItem.getId(),
                        cartItem.getUserId(),
                        cartItem.getProductId(),
                        cartItem.getQuantity(),
                        cartItem.isDeleted());
    }

    @Test
    @DisplayName("도메인 객체로 변환 시 필드가 올바르게 초기화되어야 한다")
    void givenCartItemEntity_whenCreatedDomain_thenFieldsAreInitialized() {
        // Given
        CartItemEntity cartItemEntity = CartItemEntity.create(1L, 1L, 0);

        // When
        CartItem cartItem = cartItemEntity.toDomain();

        // Then
        assertThat(cartItem).extracting( "id", "userId", "productId", "quantity", "deleted")
                .containsExactly(
                        cartItemEntity.getId(),
                        cartItemEntity.getUserId(),
                        cartItemEntity.getProductId(),
                        cartItemEntity.getQuantity(),
                        cartItemEntity.isDeleted());
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
