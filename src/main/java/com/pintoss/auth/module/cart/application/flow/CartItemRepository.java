package com.pintoss.auth.module.cart.application.flow;

import com.pintoss.auth.module.cart.application.model.CartItem;
import com.pintoss.auth.module.cart.application.model.CartItemResult;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CartItemRepository {

    List<CartItemResult> getMyCartItems(Long userId);

    List<CartItem> findByUserIdAndProductIdIn(Long userId, Set<Long> productIds);

    void saveAll(List<CartItem> cartItems);

    Optional<CartItem> findByUserIdAndId(Long userId, Long cartItemId);
}
