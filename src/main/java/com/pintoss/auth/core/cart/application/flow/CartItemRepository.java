package com.pintoss.auth.core.cart.application.flow;

import com.pintoss.auth.core.cart.domain.CartItem;
import com.pintoss.auth.core.cart.domain.CartItemResult;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CartItemRepository {

    List<CartItemResult> getMyCartItems(Long userId);

    List<CartItem> findByUserIdAndProductIdIn(Long userId, Set<Long> productIds);

    void saveAll(List<CartItem> cartItems);

    Optional<CartItem> findByUserIdAndId(Long userId, Long cartItemId);
}
