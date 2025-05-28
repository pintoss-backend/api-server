package com.pintoss.auth.api.query;

import com.pintoss.auth.common.security.SecurityContextUtils;
import com.pintoss.auth.module.order.application.model.OrderPageCommand;
import com.pintoss.auth.common.paging.PagedData;
import com.pintoss.auth.module.order.application.flow.OrderReader;
import com.pintoss.auth.module.order.application.model.OrderDetail;
import com.pintoss.auth.module.order.application.model.OrderSearchResult;
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
