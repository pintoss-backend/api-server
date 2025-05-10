package com.pintoss.auth.module.order.application;

import com.pintoss.auth.common.security.SecurityContextUtils;
import com.pintoss.auth.module.order.application.flow.OrderViewer;
import com.pintoss.auth.module.order.application.model.OrderDetail;
import com.pintoss.auth.module.order.application.model.OrderSummary;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderViewer orderViewer;

    public OrderDetail getOrderDetail(Long orderId) {
        return orderViewer.getOrderDetail(orderId);
    }

    public List<OrderSummary> getMyOrderList(){
        Long userId = SecurityContextUtils.getUserId();
        return orderViewer.getMyOrderList(userId);
    }

}
