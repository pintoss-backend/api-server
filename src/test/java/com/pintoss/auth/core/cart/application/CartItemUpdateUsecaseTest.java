package com.pintoss.auth.core.cart.application;

import com.pintoss.auth.api.security.SecurityContextUtils;
import com.pintoss.auth.core.cart.application.flow.reader.CartItemReader;
import com.pintoss.auth.core.cart.application.flow.writer.CartItemUpdater;
import com.pintoss.auth.core.cart.domain.CartItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartItemUpdateUsecaseTest {

    @Mock
    CartItemReader reader;

    @Mock
    CartItemUpdater updater;

    @InjectMocks
    CartItemUpdateUsecase usecase;

    @Test
    @DisplayName("장바구니 항목 업데이트 요청을 하면, 읽기와 업데이트 작업이 호출된다.")
    void givenCartItemUpdateRequest_whenUpdate_thenReaderAndUpdaterShouldBeCalled() {
        // Given
        Long userId = 1L;
        Long cartItemId = 10L;
        int newQuantity = 5;

        when(reader.getOrElseThrow(userId, cartItemId))
                .thenReturn(CartItem.create(userId, cartItemId, 10L, 2, false));

        // When
        try (MockedStatic<SecurityContextUtils> mockedStatic = mockStatic(SecurityContextUtils.class)) {
            mockedStatic.when(com.pintoss.auth.api.security.SecurityContextUtils::getUserId).thenReturn(userId);

            usecase.update(cartItemId, newQuantity);

            // Then
            verify(reader).getOrElseThrow(userId, cartItemId);
            verify(updater).update(any());
        }
    }
}
