package com.pintoss.auth.core.order.application;

import com.pintoss.auth.api.security.SecurityContextUtils;
import com.pintoss.auth.core.order.application.flow.viewer.OrderProjectionReader;
import com.pintoss.auth.core.order.application.dto.OrderPageCommand;
import com.pintoss.auth.api.common.paging.PagedData;
import com.pintoss.auth.core.order.application.flow.reader.OrderReader;
import com.pintoss.auth.core.order.application.dto.OrderSearchResult;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetMyOrdersUsecase {

    private final OrderReader orderReader;
    private final OrderProjectionReader orderProjectionReader;

    public GetMyOrdersUsecase(OrderReader orderReader, OrderProjectionReader orderProjectionReader) {
        this.orderReader = orderReader;
        this.orderProjectionReader = orderProjectionReader;
    }

    public PagedData<OrderSearchResult> getMyOrders(OrderPageCommand command){
        Long userId = SecurityContextUtils.getUserId();

        List<OrderSearchResult> results =  orderProjectionReader.searchByUserId(userId, command);

        long total = orderReader.countByUserId(userId, command);

        return new PagedData<>(results, total);
    }
}
