package com.pintoss.auth.storage.order;


import static com.pintoss.auth.core.order.domain.QOrder.order;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.core.order.domain.OrderSortKey;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;

public class OrderSortMapper {

    public static OrderSpecifier<?> toOrderSpecifier(OrderSortKey key, boolean asc) {
        return switch (key) {
            case CREATED_AT -> new OrderSpecifier<>(asc ? Order.ASC : Order.DESC, order.createdAt);
            case TOTAL_PRICE -> new OrderSpecifier<>(asc ? Order.ASC : Order.DESC, order.totalPrice);
            default -> throw new BadRequestException(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
        };
    }

}
