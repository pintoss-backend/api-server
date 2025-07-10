package com.pintoss.auth.core.cart.application.flow.writer;

import com.pintoss.auth.core.cart.application.repository.CartItemRepository;
import com.pintoss.auth.core.cart.domain.CartItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemAdder {

    private final CartItemRepository cartItemRepository;

    public void addAll(List<CartItem> cartItems) {
        cartItemRepository.saveAll(cartItems);
    }

}
