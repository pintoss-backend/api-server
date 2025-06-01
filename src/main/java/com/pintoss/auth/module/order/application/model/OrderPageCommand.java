package com.pintoss.auth.module.order.application.model;

import com.pintoss.auth.common.paging.SortDirection;
import com.pintoss.auth.module.payment.application.PaymentMethodType;
import java.time.LocalDate;
import java.util.List;

public record OrderPageCommand(
    long offset,
    long limit,
    OrderSortKey sortKey,
    SortDirection sort,
    List<OrderStatus> statuses,
    PaymentMethodType paymentMethodType,
    LocalDate startDate,
    LocalDate endDate
) {
    public OrderPageCommand(long offset, long limit, OrderSortKey sortKey, SortDirection sort) {
        this(offset, limit, sortKey, sort, null, null, null, null);
    }

    public boolean hasPaymentMethodType() {
        return paymentMethodType != null;
    }

    public boolean hasDateRange() {
        return startDate != null && endDate != null;
    }
}
