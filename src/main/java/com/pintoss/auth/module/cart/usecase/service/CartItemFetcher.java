package com.pintoss.auth.module.cart.usecase.service;

import com.pintoss.auth.module.cart.model.CartItem;
import com.pintoss.auth.module.cart.usecase.dto.CartItemResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemFetcher {

    private final CartItemRepository cartItemRepository;

    public List<CartItemResult> fetchCartItems(Long userId) {
        return cartItemRepository.fetchCartItems(userId);
    }
}
