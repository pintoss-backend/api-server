package com.pintoss.auth.core.cart.application.flow.validator;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.NotFoundException;
import com.pintoss.auth.core.cart.application.repository.CartItemRepository;
import com.pintoss.auth.core.cart.domain.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemValidator {

    private final CartItemRepository cartItemRepository;

    public CartItemValidator(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem getOrThrowIfNotExists(Long userId, Long cartItemId) {
        return cartItemRepository.findByUserIdAndId(userId, cartItemId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CART_ITEM));
    }
}
