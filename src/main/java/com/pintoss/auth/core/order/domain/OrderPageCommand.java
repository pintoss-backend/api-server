package com.pintoss.auth.core.order.domain;

import com.pintoss.auth.common.paging.SortDirection;
import com.pintoss.auth.core.payment.application.PaymentMethodType;
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
    public boolean hasPaymentMethodType() {
        return paymentMethodType != null;
    }

    public boolean hasDateRange() {
        return startDate != null && endDate != null;
    }
}
