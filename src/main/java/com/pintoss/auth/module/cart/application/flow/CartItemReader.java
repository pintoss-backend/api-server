package com.pintoss.auth.module.cart.application.flow;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.NotFoundException;
import com.pintoss.auth.module.cart.application.model.CartItem;
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

    public CartItem readByUserIdAndId(Long userId, Long cartItemId) {
        return cartItemRepository.findByUserIdAndId(userId, cartItemId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CART_ITEM));
    }
}
