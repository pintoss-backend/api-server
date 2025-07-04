package com.pintoss.auth.storage.cart;

import com.pintoss.auth.core.cart.domain.CartItem;
import com.pintoss.auth.core.cart.domain.CartItemResult;
import com.pintoss.auth.core.cart.application.flow.CartItemRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartItemRepositoryImpl implements CartItemRepository {

    private final CartItemJpaRepository jpaRepository;
    private final CartItemQueryDslRepository queryDslRepository;

    @Override
    public void saveAll(List<CartItem> cartItems) {
        jpaRepository.saveAll(cartItems);
    }

    @Override
    public List<CartItemResult> getMyCartItems(Long userId) {
        return queryDslRepository.getMyCartItems(userId);
    }

    @Override
    public List<CartItem> findByUserIdAndProductIdIn(Long userId, Set<Long> productIds) {
        return queryDslRepository.findByUserIdAndProductIdIn(userId, productIds);
    }

    @Override
    public Optional<CartItem> findByUserIdAndId(Long userId, Long cartItemId) {
        return queryDslRepository.findByUserIdAndId(userId, cartItemId);
    }
}
