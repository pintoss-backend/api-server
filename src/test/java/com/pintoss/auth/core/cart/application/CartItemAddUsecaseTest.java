package com.pintoss.auth.core.cart.application;

import com.pintoss.auth.api.security.SecurityContextUtils;
import com.pintoss.auth.core.cart.application.dto.CartItemAddRequest;
import com.pintoss.auth.core.cart.application.flow.reader.CartItemReader;
import com.pintoss.auth.core.cart.application.flow.writer.CartItemAdder;
import com.pintoss.auth.core.cart.domain.CartItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartItemAddUsecaseTest {

    @InjectMocks
    CartItemAddUsecase usecase;

    @Mock
    CartItemReader reader;

    @Mock
    CartItemAdder adder;

    @Test
    @DisplayName("장바구니에 아무 항목도 없을 때 상품 추가 요청을 하면, 읽기와 추가 작업이 호출된다.")
    void givenEmptyCartItem_whenAddRequest_thenReaderAndAdderShouldBeCalled() {
        // Given
        Long userId = 1L;
        List<CartItemAddRequest> command = List.of(
                createRequest(10L, 2),
                createRequest(20L, 3)
        );

        try (MockedStatic<SecurityContextUtils> mockedStatic = mockStatic(SecurityContextUtils.class)) {
            mockedStatic.when(SecurityContextUtils::getUserId).thenReturn(userId);

            given(reader.readSelectedCartItems(eq(userId), anySet()))
                    .willReturn(List.of());

            // When
            usecase.addCartItem(command);

            // Then
            verify(reader, times(1)).readSelectedCartItems(eq(userId), anySet());
            verify(adder, times(1)).addAll(anyList());
        }
    }

    @Test
    @DisplayName("장바구니에 항목이 있을 때 상품 추가 요청을 하면, 읽기와 추가 작업이 호출된다.")
    void givenCartItem_whenAddRequest_thenReaderAndAdderShouldBeCalled() {
        // Given
        Long userId = 1L;
        List<CartItemAddRequest> command = List.of(
                createRequest(10L, 2),
                createRequest(20L, 3)
        );

        try (MockedStatic<SecurityContextUtils> mockedStatic = mockStatic(SecurityContextUtils.class)) {
            mockedStatic.when(SecurityContextUtils::getUserId).thenReturn(userId);

            given(reader.readSelectedCartItems(eq(userId), anySet()))
                    .willReturn(List.of(
                            CartItem.create(userId, 10L, 3)
                    ));

            // When
            usecase.addCartItem(command);

            // Then
            verify(reader, times(1)).readSelectedCartItems(eq(userId), anySet());
            verify(adder, times(1)).addAll(anyList());
        }
    }

    @Test
    @DisplayName("장바구니에 없는 상품을 추가하면 새 항목이 생성된다.")
    void givenCartItem_whenAddRequest_thenCartItemShouldBeAddedOrUpdated() {
        // Given
        Long userId = 1L;
        List<CartItemAddRequest> command = List.of(
                createRequest(10L, 2),
                createRequest(20L, 3)
        );

        try (MockedStatic<SecurityContextUtils> mockedStatic = mockStatic(SecurityContextUtils.class)) {
            mockedStatic.when(SecurityContextUtils::getUserId).thenReturn(userId);

            given(reader.readSelectedCartItems(eq(userId), anySet()))
                    .willReturn(List.of());

            // When
            usecase.addCartItem(command);

            // Then
            verify(adder).addAll(argThat(cartItems -> {
                boolean hasUpdated10L = cartItems.stream().anyMatch(item ->
                        item.getProductId().equals(10L) && item.getQuantity() == 2);

                boolean hasNew20L = cartItems.stream().anyMatch(item ->
                        item.getProductId().equals(20L) && item.getQuantity() == 3);

                return hasUpdated10L && hasNew20L && cartItems.size() == 2;
            }));
        }
    }

    @Test
    @DisplayName("장바구니에 이미 있는 상품을 추가하면 해당 상품의 수량이 합산되어 업데이트된다.")
    void givenCartItem_whenAddRequest_thenCartItemShouldBeUpdated() {
        // Given
        Long userId = 1L;
        List<CartItemAddRequest> command = List.of(
                createRequest(10L, 4)
        );

        try (MockedStatic<SecurityContextUtils> mockedStatic = mockStatic(SecurityContextUtils.class)) {
            mockedStatic.when(SecurityContextUtils::getUserId).thenReturn(userId);

            given(reader.readSelectedCartItems(eq(userId), anySet()))
                    .willReturn(List.of(
                            CartItem.create(userId, 10L, 3)
                    ));

            // When
            usecase.addCartItem(command);

            // Then
            verify(adder).addAll(argThat(cartItems -> {
                boolean hasUpdated10L = cartItems.stream().anyMatch(item ->
                        item.getProductId().equals(10L) && item.getQuantity() == 7);

                return hasUpdated10L && cartItems.size() == 1;
            }));
        }
    }

    @Test
    @DisplayName("장바구니에 상품을 추가하면 있는 상품의 수량은 합산되고 없는 상품은 새로 추가된다.")
    void givenCartItem_whenAddRequest_thenCartItemShouldAddedAndUpdated() {
        // Given
        Long userId = 1L;
        List<CartItemAddRequest> command = List.of(
                createRequest(10L, 4),
                createRequest(20L, 3)
        );

        try (MockedStatic<SecurityContextUtils> mockedStatic = mockStatic(SecurityContextUtils.class)) {
            mockedStatic.when(SecurityContextUtils::getUserId).thenReturn(userId);

            given(reader.readSelectedCartItems(eq(userId), anySet()))
                    .willReturn(List.of(
                            CartItem.create(userId, 10L, 3)
                    ));

            // When
            usecase.addCartItem(command);

            // Then
            verify(adder).addAll(argThat(cartItems -> {
                boolean hasUpdated10L = cartItems.stream().anyMatch(item ->
                        item.getProductId().equals(10L) && item.getQuantity() == 7);

                boolean hasNew20L = cartItems.stream().anyMatch(item ->
                        item.getProductId().equals(20L) && item.getQuantity() == 3);

                return hasUpdated10L && hasNew20L && cartItems.size() == 2;
            }));
        }
    }

    private static CartItemAddRequest createRequest(Long productId, int quantity) {
        return CartItemAddRequest.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
    }
}
