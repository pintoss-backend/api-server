package com.pintoss.auth.core.cart.application;

import com.pintoss.auth.api.security.SecurityContextUtils;
import com.pintoss.auth.core.cart.domain.CartItem;
import com.pintoss.auth.core.cart.application.flow.CartItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemDeleteUseCase {

    private final CartItemReader cartItemReader;

    @Transactional
    public void deleteCartItem(Long cartItemId) {
        Long userId = SecurityContextUtils.getUserId();

        CartItem cartItem = cartItemReader.readByUserIdAndId(userId, cartItemId);
        cartItem.deleted();
    }

}
