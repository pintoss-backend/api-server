package com.pintoss.auth.module.order.store;

import static com.pintoss.auth.module.order.application.model.QOrder.order;
import static com.pintoss.auth.module.order.application.model.QOrderItem.orderItem;
import static com.pintoss.auth.module.payment.store.QPaymentEntity.paymentEntity;

import com.pintoss.auth.module.order.application.model.OrderDetail;
import com.pintoss.auth.module.order.application.model.OrderItemDetail;
import com.pintoss.auth.module.order.application.model.OrderPageCommand;
import com.pintoss.auth.module.order.application.model.OrderSearchResult;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<OrderDetail> findDetailByOrderId(Long orderId) {

        List<Tuple> results = queryFactory
            .select(
                order.id,
                order.orderNo,
                order.status,
                order.totalPrice,
                order.ordererName,
                order.ordererPhone,
                order.createdAt,
                paymentEntity.paymentMethodType,
                // ✅ orderItem 필드도 추가
                orderItem.voucherIssuerName,
                orderItem.voucherName,
                orderItem.price,
                orderItem.pinNum,
                orderItem.status
            )
            .from(order)
            .leftJoin(order.orderItems, orderItem)
            .leftJoin(paymentEntity).on(paymentEntity.id.eq(order.paymentId))
            .where(order.id.eq(orderId))
            .fetch();
        if (results.isEmpty()) {
            return Optional.empty();
        }

        Tuple first = results.get(0);

        OrderDetail orderDetail = new OrderDetail(
            first.get(order.id),
            first.get(order.orderNo),
            first.get(paymentEntity.paymentMethodType),
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

    public List<OrderSearchResult> searchByUserIdAndWithCondition(Long userId, OrderPageCommand command) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(order.ordererId.eq(userId));

        if(command.statuses() != null && command.statuses().isEmpty()) {
            builder.and(order.status.in(command.statuses()));
        }
        if (command.hasPaymentMethodType()) {
            builder.and(paymentEntity.paymentMethodType.eq(command.paymentMethodType()));
        }
        if (command.hasDateRange()) {
            builder.and(order.createdAt.between(
                command.startDate().atStartOfDay(),
                command.endDate().atTime(LocalTime.MAX)
            ));
        }

        OrderSpecifier<?> sort = OrderSortMapper.toOrderSpecifier(
            command.sortKey(),
            command.sort().isAcs()
        );

        return queryFactory.select(
                Projections.constructor(OrderSearchResult.class,
                    order.id.as("orderId"),
                    order.orderNo.as("orderNo"),
                    order.status,
                    paymentEntity.paymentMethodType,
                    order.createdAt.as("orderDate"),
                    order.totalPrice.as("price")
                )
            )
            .from(order)
            .leftJoin(paymentEntity).on(paymentEntity.id.eq(order.paymentId))
            .where(builder)
            .orderBy(sort)
            .offset(command.offset())
            .limit(command.limit())
            .fetch();
    }

    public long countByUserIdWithCondition(Long userId, OrderPageCommand command) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(order.ordererId.eq(userId));

        if(command.statuses() != null && !command.statuses().isEmpty()) {
            builder.and(order.status.in(command.statuses()));
        }
        if (command.hasPaymentMethodType()) {
            builder.and(paymentEntity.paymentMethodType.eq(command.paymentMethodType()));
        }
        if (command.hasDateRange()) {
            builder.and(order.createdAt.between(
                command.startDate().atStartOfDay(),
                command.endDate().atTime(LocalTime.MAX)
            ));
        }

        return queryFactory.select(order.count())
            .from(order)
            .leftJoin(paymentEntity).on(paymentEntity.id.eq(order.paymentId))
            .where(builder)
            .fetchOne();
    }
}
