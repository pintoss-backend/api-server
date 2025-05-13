package com.pintoss.auth.module.cart.external;

import static com.pintoss.auth.module.cart.model.QCartItem.cartItem;
import static com.pintoss.auth.module.voucher.model.QVoucher.voucher;
import static com.pintoss.auth.module.voucher.model.QVoucherIssuer.voucherIssuer;

import com.pintoss.auth.module.cart.model.CartItem;
import com.pintoss.auth.module.cart.usecase.dto.CartItemResult;
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

    public List<CartItemResult> fetchCartItems(Long userId) {

        return queryFactory
            .select(Projections.constructor(CartItemResult.class,
                cartItem.id.as("id"),
                voucher.id.as("productId"),
                cartItem.quantity,
                voucher.name,
                voucher.price,
                voucherIssuer.imageUrl
            ))
            .from(cartItem)
            .join(voucher).on(cartItem.productId.eq(voucher.id))
            .join(voucherIssuer).on(voucher.voucherIssuerId.eq(voucherIssuer.id))
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
