package com.pintoss.auth.api.order.query;

import com.pintoss.auth.api.security.SecurityContextUtils;
import com.pintoss.auth.core.order.domain.OrderPageCommand;
import com.pintoss.auth.api.common.paging.PagedData;
import com.pintoss.auth.core.order.application.flow.OrderReader;
import com.pintoss.auth.core.order.domain.OrderDetail;
import com.pintoss.auth.core.order.domain.OrderSearchResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderReader orderReader;

    public OrderDetail getOrderDetail(Long orderId) {
        return orderReader.getDetailById(orderId);
    }

    public PagedData<OrderSearchResult> getMyOrderList(OrderPageCommand command){
        Long userId = SecurityContextUtils.getUserId();

        List<OrderSearchResult> results =  orderReader.searchByUserId(userId, command);
        long total = orderReader.countByUserId(userId, command);

        return new PagedData<>(results, total);
    }
}
