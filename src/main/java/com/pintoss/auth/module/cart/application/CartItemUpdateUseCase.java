package com.pintoss.auth.module.cart.application;

import com.pintoss.auth.common.security.SecurityContextUtils;
import com.pintoss.auth.module.cart.application.model.CartItem;
import com.pintoss.auth.module.cart.application.flow.CartItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemUpdateUseCase {

    private final CartItemReader cartItemReader;

    @Transactional
    public void update(Long cartItemId, int quantity) {
        Long userId = SecurityContextUtils.getUserId();

        CartItem cartItem = cartItemReader.readByUserIdAndId(userId, cartItemId);
        cartItem.calculateQuantity(quantity);
    }
}
