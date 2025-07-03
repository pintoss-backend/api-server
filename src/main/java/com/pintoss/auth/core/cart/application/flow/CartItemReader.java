package com.pintoss.auth.core.cart.application.flow;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.NotFoundException;
import com.pintoss.auth.core.cart.application.model.CartItem;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemReader {

    private final CartItemRepository cartItemRepository;

    // 이 메서드는 사용자의 장바구니에 있는 상품을 조회하는 메서드입니다.
    public List<CartItem> readSelectedCartItems(Long userId, Set<Long> productIds) {
        return cartItemRepository.findByUserIdAndProductIdIn(userId, productIds);
    }

    // 이 메서드는 특정 사용자의 장바구니에서 특정 상품을 조회하는 메서드입니다.
    public CartItem readByUserIdAndId(Long userId, Long cartItemId) {
        return cartItemRepository.findByUserIdAndId(userId, cartItemId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CART_ITEM));
    }
}
