package com.pintoss.auth.core.cart.application;

import com.pintoss.auth.api.support.security.SecurityContextUtils;
import com.pintoss.auth.core.cart.application.flow.reader.CartItemReader;
import com.pintoss.auth.core.cart.application.flow.writer.CartItemUpdater;
import com.pintoss.auth.core.cart.domain.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemUpdateUsecase {

    private final CartItemReader cartItemReader;
    private final CartItemUpdater cartItemUpdater;

    @Transactional
    public void update(Long cartItemId, int quantity) {
        Long userId = SecurityContextUtils.getUserId();

        CartItem cartItem = cartItemReader.getOrElseThrow(userId, cartItemId);
        cartItem.calculateQuantity(quantity);
        cartItemUpdater.update(cartItem);
    }
}
