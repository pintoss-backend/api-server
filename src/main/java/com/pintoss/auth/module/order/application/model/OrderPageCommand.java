package com.pintoss.auth.module.order.application.model;

import com.pintoss.auth.common.paging.SortDirection;
import com.pintoss.auth.module.payment.application.PaymentMethodType;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class OrderPageCommand {

    private long offset;
    private long limit;
    private OrderSortKey sortKey;
    private SortDirection sort;

    private OrderStatus status;
    private PaymentMethodType paymentMethodType;
    private LocalDate startDate;
    private LocalDate endDate;

    public OrderPageCommand(long offset, long limit, OrderSortKey sortKey, SortDirection sort,
        OrderStatus status, PaymentMethodType paymentMethodType, LocalDate startDate,
        LocalDate endDate) {
        this.offset = offset;
        this.limit = limit;
        this.sortKey = sortKey;
        this.sort = sort;
        this.status = status;
        this.paymentMethodType = paymentMethodType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public OrderPageCommand(long offset, long limit, OrderSortKey sortKey, SortDirection sort) {
        this.offset = offset;
        this.limit = limit;
        this.sortKey = sortKey;
        this.sort = sort;
    }

    public boolean hasStatus() {
        return status != null;
    }

    public boolean hasPaymentMethodType() {
        return paymentMethodType != null;
    }

    public boolean hasDateRange() {
        return startDate != null && endDate != null;
    }
}
