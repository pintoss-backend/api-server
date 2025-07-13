package com.pintoss.auth.core.cart.application.flow.writer;

import com.pintoss.auth.core.cart.application.repository.CartItemRepository;
import com.pintoss.auth.core.cart.domain.CartItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartItemUpdaterTest {

    @Mock
    CartItemRepository repository;

    @InjectMocks
    CartItemUpdater updater;

    @Test
    @DisplayName("장바구니 항목을 삭제로 표시할 때, 업데이트가 호출된다.")
    void givenCartItem_whenMarkAsDeleted_thenUpdateShouldBeCalled() {
        // Given
        CartItem cartItem = createCartItem(1L, 10L, 2);

        // When
        updater.markAsDeleted(cartItem);

        // Then
        verify(repository).update(cartItem);
    }

    @Test
    @DisplayName("장바구니 항목을 삭제로 표시할 때, 항목의 상태가 변경된다.")
    void givenCartItem_whenMarkAsDeleted_thenCartItemShouldBeDeleted() {
        // Given
        CartItem cartItem = createCartItem(1L, 10L, 2);

        // When
        updater.markAsDeleted(cartItem);

        // Then
        assert cartItem.isDeleted();  // Assuming isDeleted() method exists in CartItem
    }

    private static CartItem createCartItem(Long id, Long productId, int quantity) {
        return CartItem.builder()
                .id(id)
                .productId(productId)
                .quantity(quantity)
                .build();
    }

}
