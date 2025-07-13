package com.pintoss.auth.core.cart.application.flow.writer;

import com.pintoss.auth.core.cart.application.repository.CartItemRepository;
import com.pintoss.auth.core.cart.domain.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemUpdater {

    private final CartItemRepository cartItemRepository;

    public void markAsDeleted(CartItem cartItem) {
        cartItem.deleted();
        cartItemRepository.update(cartItem);
    }

}
