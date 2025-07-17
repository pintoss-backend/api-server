package com.pintoss.auth.storage.cart;

import com.pintoss.auth.core.cart.application.dto.CartItemView;
import com.pintoss.auth.core.cart.application.repository.CartItemRepository;
import com.pintoss.auth.core.cart.domain.CartItem;
import com.pintoss.auth.core.exception.CoreErrorCode;
import com.pintoss.auth.core.exception.HttpErrorType;
import com.pintoss.auth.core.exception.CoreException;
import com.pintoss.auth.storage.cart.jpa.CartItemJpaRepository;
import com.pintoss.auth.storage.cart.jpa.entity.CartItemEntity;
import com.pintoss.auth.storage.cart.querydsl.CartItemQueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CartItemRepositoryImpl implements CartItemRepository {

    private final CartItemJpaRepository jpaRepository;
    private final CartItemQueryDslRepository queryDslRepository;

    @Override
    public void saveAll(List<CartItem> cartItems) {
        List<CartItemEntity> entities = cartItems.stream()
                .map(CartItemEntity::from)
                .toList();
        jpaRepository.saveAll(entities);
    }

    @Override
    public List<CartItemView> getMyCartItems(Long userId) {
        return queryDslRepository.getMyCartItems(userId);
    }

    @Override
    public List<CartItem> findByUserIdAndProductIdIn(Long userId, Set<Long> productIds) {
        List<CartItemEntity> entities = queryDslRepository.findByUserIdAndProductIdIn(userId, productIds);
        
        return entities.stream()
                .map(CartItemEntity::toDomain)
                .toList();
    }

    @Override
    public CartItem findByUserIdAndIdOrElseThrow(Long userId, Long cartItemId) {
        CartItemEntity entity = queryDslRepository.findByUserIdAndId(userId, cartItemId)
                .orElseThrow(() -> new CoreException(CoreErrorCode.CART_ITEM_NOT_FOUND));

        return entity.toDomain();
    }

    @Override
    public void update(CartItem cartItem) {
        CartItemEntity entity = jpaRepository.findById(cartItem.getId())
                .orElseThrow(() -> new CoreException(CoreErrorCode.CART_ITEM_NOT_FOUND));
        entity.update(cartItem.getQuantity(), cartItem.isDeleted());
    }
}
