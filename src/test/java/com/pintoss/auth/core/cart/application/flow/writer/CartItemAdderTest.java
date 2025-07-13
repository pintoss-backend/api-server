package com.pintoss.auth.core.cart.application.flow.writer;

import com.pintoss.auth.core.cart.application.repository.CartItemRepository;
import com.pintoss.auth.core.cart.domain.CartItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartItemAdderTest {

    @Mock
    CartItemRepository repository;

    @InjectMocks
    CartItemAdder adder;

    @Test
    @DisplayName("장바구니 항목을 추가할 때, 저장소가 호출된다")
    void givenCartItems_whenAddAll_thenSaveAllShouldBeCalled() {
        // Given
        CartItem cartItem1 = createCartItem(1L, 10L, 2);
        CartItem cartItem2 = createCartItem(2L, 20L, 3);
        List<CartItem> cartItems = List.of(cartItem1, cartItem2);

        // When
        adder.addAll(cartItems);

        // Then
        verify(repository).saveAll(cartItems);
    }

    private static CartItem createCartItem(Long id, Long productId, int quantity) {
        return CartItem.builder()
                .id(id)
                .productId(productId)
                .quantity(quantity)
                .build();
    }

}
