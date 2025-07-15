package com.pintoss.auth.api.order.dto;

import com.pintoss.auth.api.support.paging.SortDirection;
import com.pintoss.auth.core.order.application.dto.OrderPageCommand;
import com.pintoss.auth.core.order.domain.OrderSortKey;
import com.pintoss.auth.core.order.domain.OrderStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderPageRequest {
    private Integer page;
    private Integer size;

    private String keyword;
    private List<OrderStatus> statuses;

    private LocalDate startDate;
    private LocalDate endDate;

    private OrderSortKey sortKey = OrderSortKey.CREATED_AT;
    private SortDirection sort = SortDirection.DESC;

    public int getOffset() {
        return (getPage() - 1) * getSize();
    }

    public int getPage() {
        return page != null ? page : 1;
    }

    public int getSize() {
        return size != null ? size : 10;
    }

    public OrderSortKey getSortKey() {
        return sortKey != null ? sortKey : OrderSortKey.CREATED_AT;
    }

    public SortDirection getSort() {
        return sort != null ? sort : SortDirection.DESC;
    }
    public OrderPageCommand to() {
        return new OrderPageCommand(getOffset(), getSize(), sortKey, sort, statuses, null, startDate, endDate);
    }
}
