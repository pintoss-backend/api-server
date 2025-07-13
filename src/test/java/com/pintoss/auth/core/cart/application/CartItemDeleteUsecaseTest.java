package com.pintoss.auth.core.cart.application;

import com.pintoss.auth.api.security.SecurityContextUtils;
import com.pintoss.auth.core.cart.application.flow.reader.CartItemReader;
import com.pintoss.auth.core.cart.application.flow.writer.CartItemUpdater;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartItemDeleteUsecaseTest {

    @Mock
    CartItemReader reader;

    @Mock
    CartItemUpdater updater;

    @InjectMocks
    CartItemDeleteUsecase usecase;

    @Test
    @DisplayName("장바구니 삭제 요청을 하면, 읽기와 업데이트 작업이 호출된다.")
    void givenCartItemDeleteRequest_whenDelete_thenReaderAndUpdaterShouldBeCalled() {
        // Given
        Long userId = 1L;
        Long cartItemId = 10L;

        try (MockedStatic<SecurityContextUtils> mockedStatic = mockStatic(SecurityContextUtils.class)) {
            mockedStatic.when(com.pintoss.auth.api.security.SecurityContextUtils::getUserId).thenReturn(userId);

            // When
            usecase.deleteCartItem(cartItemId);

            // Then
            verify(reader).getOrElseThrow(userId, cartItemId);
            verify(updater).markAsDeleted(any());
        }
    }

}
