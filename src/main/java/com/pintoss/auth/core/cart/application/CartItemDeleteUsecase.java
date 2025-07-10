package com.pintoss.auth.core.cart.application;

import com.pintoss.auth.api.security.SecurityContextUtils;
import com.pintoss.auth.core.cart.application.flow.validator.CartItemValidator;
import com.pintoss.auth.core.cart.domain.CartItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartItemDeleteUsecase {

    private final CartItemValidator cartItemValidator;

    public CartItemDeleteUsecase(CartItemValidator cartItemValidator) {
        this.cartItemValidator = cartItemValidator;
    }

    @Transactional
    public void deleteCartItem(Long cartItemId) {
        Long userId = SecurityContextUtils.getUserId();

        CartItem cartItem = cartItemValidator.getOrThrowIfNotExists(userId, cartItemId);
        cartItem.deleted();
    }
}
