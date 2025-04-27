package com.pintoss.auth.module.cart.usecase.service;

import com.pintoss.auth.module.cart.model.CartItem;
import com.pintoss.auth.module.cart.usecase.dto.CartItemResult;
import java.util.List;
import java.util.Set;

public interface CartItemRepository {

    List<CartItemResult> fetchCartItems(Long userId);

    List<CartItem> findByUserIdAndProductIdIn(Long userId, Set<Long> productIds);

    void saveAll(List<CartItem> cartItems);
}
