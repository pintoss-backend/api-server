package com.pintoss.auth.core.cart.application.repository;

import com.pintoss.auth.core.cart.application.dto.CartItemView;
import com.pintoss.auth.core.cart.domain.CartItem;

import java.util.List;
import java.util.Set;

public interface CartItemRepository {

    List<CartItemView> getMyCartItems(Long userId);

    List<CartItem> findByUserIdAndProductIdIn(Long userId, Set<Long> productIds);

    void saveAll(List<CartItem> cartItems);

    CartItem findByUserIdAndIdOrElseThrow(Long userId, Long cartItemId);

    void update(CartItem cartItem);
}
