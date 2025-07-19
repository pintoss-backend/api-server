package com.pintoss.auth.core.order.application.dto;

import com.pintoss.auth.api.support.paging.SortDirection;
import com.pintoss.auth.core.order.domain.OrderSortKey;
import com.pintoss.auth.core.order.domain.OrderStatus;
import com.pintoss.auth.core.payment.domain.PaymentMethodType;
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
