package com.pintoss.auth.core.cart.store;

import static com.pintoss.auth.core.cart.application.model.QCartItem.cartItem;
import static com.pintoss.auth.core.voucher.store.QVoucherEntity.voucherEntity;
import static com.pintoss.auth.core.voucher.store.QVoucherIssuerEntity.voucherIssuerEntity;

import com.pintoss.auth.core.cart.domain.CartItem;
import com.pintoss.auth.core.cart.domain.CartItemResult;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartItemQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<CartItemResult> getMyCartItems(Long userId) {

        return queryFactory
            .select(Projections.constructor(CartItemResult.class,
                cartItem.id.as("id"),
                voucherEntity.id.as("productId"),
                cartItem.quantity,
                voucherEntity.name,
                voucherEntity.price,
                voucherIssuerEntity.imageUrl
            ))
            .from(cartItem)
            .join(voucherEntity).on(cartItem.productId.eq(voucherEntity.id))
            .join(voucherIssuerEntity).on(voucherEntity.voucherIssuerId.eq(voucherIssuerEntity.id))
            .where(
                cartItem.userId.eq(userId),
                cartItem.isDeleted.isFalse()
            )
            .fetch();
    }

    public List<CartItem> findByUserIdAndProductIdIn(Long userId, Set<Long> productIds) {
        return queryFactory
            .selectFrom(cartItem)
            .where(
                cartItem.userId.eq(userId),
                cartItem.productId.in(productIds),
                cartItem.isDeleted.isFalse()
            )
            .fetch();
    }

    public Optional<CartItem> findByUserIdAndId(Long userId, Long cartItemId) {
        return Optional.ofNullable(
            queryFactory
                .selectFrom(cartItem)
                .where(
                    cartItem.userId.eq(userId),
                    cartItem.id.eq(cartItemId),
                    cartItem.isDeleted.isFalse()
                )
                .fetchOne()
        );
    }
}
