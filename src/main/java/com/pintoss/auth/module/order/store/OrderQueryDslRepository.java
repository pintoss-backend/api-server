package com.pintoss.auth.module.order.store;

import static com.pintoss.auth.module.order.application.model.QOrder.order;
import static com.pintoss.auth.module.order.application.model.QOrderItem.orderItem;

import com.pintoss.auth.module.order.application.model.OrderDetail;
import com.pintoss.auth.module.order.application.model.OrderItemDetail;
import com.pintoss.auth.module.order.application.model.OrderSummary;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<OrderDetail> getOrderDetail(Long orderId) {

        List<Tuple> results = queryFactory
            .select(
                order.id,
                order.orderNo,
                order.paymentMethodType,
                order.status,
                order.totalPrice,
                order.ordererName,
                order.ordererPhone,
                order.createdAt,
                // ✅ orderItem 필드도 추가
                orderItem.voucherIssuerName,
                orderItem.voucherName,
                orderItem.price,
                orderItem.pinNum,
                orderItem.status
            )
            .from(order)
            .leftJoin(order.orderItems, orderItem)
            .where(order.id.eq(orderId))
            .fetch();
        if (results.isEmpty()) {
            return Optional.empty();
        }

        Tuple first = results.get(0);

        OrderDetail orderDetail = new OrderDetail(
            first.get(order.id),
            first.get(order.orderNo),
            first.get(order.paymentMethodType),
            first.get(order.status),
            first.get(order.totalPrice),
            first.get(order.ordererName),
            first.get(order.ordererPhone),
            first.get(order.createdAt),
            new ArrayList<>()
        );
        for(Tuple t: results) {
            if (t.get(orderItem.voucherName) != null) {
                OrderItemDetail item = new OrderItemDetail(
                    t.get(orderItem.voucherIssuerName),
                    t.get(orderItem.voucherName),
                    t.get(orderItem.price),
                    t.get(orderItem.pinNum),
                    t.get(orderItem.status)
                );
                orderDetail.getItems().add(item);
            }
        }
        return Optional.of(orderDetail);
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
