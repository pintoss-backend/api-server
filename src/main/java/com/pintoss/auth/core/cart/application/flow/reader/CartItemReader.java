package com.pintoss.auth.core.cart.application.flow.reader;

import com.pintoss.auth.core.cart.application.repository.CartItemRepository;
import com.pintoss.auth.core.cart.domain.CartItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class CartItemReader {

    private final CartItemRepository cartItemRepository;

    public CartItemReader(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    // 이 메서드는 사용자의 장바구니에 있는 상품을 조회하는 메서드입니다.
    public List<CartItem> readSelectedCartItems(Long userId, Set<Long> productIds) {
        return cartItemRepository.findByUserIdAndProductIdIn(userId, productIds);
    }

    // 이 메서드는 사용자의 장바구니에 있는 상품을 조회하고 없으면 예외를 던지는 메서드입니다.
    public CartItem getOrElseThrow(Long userId, Long cartItemId) {
        return cartItemRepository.findByUserIdAndIdOrElseThrow(userId, cartItemId);
    }
}
