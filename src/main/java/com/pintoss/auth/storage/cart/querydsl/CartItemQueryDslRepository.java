package com.pintoss.auth.storage.cart.querydsl;

import com.pintoss.auth.core.cart.application.dto.CartItemView;
import com.pintoss.auth.storage.cart.jpa.entity.CartItemEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.pintoss.auth.storage.cart.jpa.entity.QCartItemEntity.cartItemEntity;
import static com.pintoss.auth.storage.voucher.jpa.entity.QVoucherEntity.voucherEntity;
import static com.pintoss.auth.storage.voucher.jpa.entity.QVoucherIssuerEntity.voucherIssuerEntity;

@Repository
@RequiredArgsConstructor
public class CartItemQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<CartItemView> getMyCartItems(Long userId) {

        return queryFactory
                .select(Projections.constructor(CartItemView.class,
                        cartItemEntity.id.as("id"),
                        voucherEntity.id.as("productId"),
                        cartItemEntity.quantity,
                        voucherEntity.name,
                        voucherEntity.price,
                        voucherIssuerEntity.imageUrl
                ))
                .from(cartItemEntity)
                .join(voucherEntity).on(cartItemEntity.productId.eq(voucherEntity.id))
                .join(voucherIssuerEntity).on(voucherEntity.voucherIssuerId.eq(voucherIssuerEntity.id))
                .where(
                        cartItemEntity.userId.eq(userId),
                        cartItemEntity.isDeleted.isFalse()
                )
                .fetch();
    }

    public List<CartItemEntity> findByUserIdAndProductIdIn(Long userId, Set<Long> productIds) {
        return queryFactory
                .selectFrom(cartItemEntity)
                .where(
                        cartItemEntity.userId.eq(userId),
                        cartItemEntity.productId.in(productIds),
                        cartItemEntity.isDeleted.isFalse()
                )
                .fetch();
    }

    public Optional<CartItemEntity> findByUserIdAndId(Long userId, Long cartItemId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(cartItemEntity)
                        .where(
                                cartItemEntity.userId.eq(userId),
                                cartItemEntity.id.eq(cartItemId),
                                cartItemEntity.isDeleted.isFalse()
                        )
                        .fetchOne()
        );
    }
}
