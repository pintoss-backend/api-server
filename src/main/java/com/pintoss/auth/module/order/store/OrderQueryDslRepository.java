package com.pintoss.auth.module.order.store;

import static com.pintoss.auth.module.order.application.model.QOrder.order;
import static com.pintoss.auth.module.user.model.QUser.user;

import com.pintoss.auth.module.order.application.model.OrderDetail;
import com.pintoss.auth.module.order.application.model.OrderSummary;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<OrderDetail> getOrderDetail(Long orderId) {
        return Optional.ofNullable(
            queryFactory.select(
                    Projections.constructor(OrderDetail.class,
                        order.id.as("orderId"),
                        order.orderNo.as("orderNo"),
                        order.paymentMethodType,
                        order.status.as("orderStatus"),
                        order.totalPrice,
                        order.ordererName,
                        order.ordererPhone,
                        order.createdAt.as("orderDate")
                    )
                )
                .from(order)
                .innerJoin(user).on(user.id.eq(order.ordererId))
                .where(order.id.eq(orderId))
                .fetchOne()
        );
    }

    public List<OrderSummary> getMyOrderList(Long userId) {
        return queryFactory.select(
            Projections.constructor(OrderSummary.class,
                order.id.as("orderId"),
                order.orderNo.as("orderNo"),
                order.status,
                order.paymentMethodType,
                order.createdAt.as("orderDate"),
                order.totalPrice.as("price")
            )
        )
            .from(order)
            .where(order.ordererId.eq(userId))
            .fetch();
    }
}
