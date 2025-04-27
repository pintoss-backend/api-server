package com.pintoss.auth.module.cart.usecase.service;

import com.pintoss.auth.module.cart.model.CartItem;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemReader {

    private final CartItemRepository cartItemRepository;

    public List<CartItem> readByUserIdAndProductIdIn(Long userId, Set<Long> productIds) {
        return cartItemRepository.findByUserIdAndProductIdIn(userId, productIds);
    }

}
