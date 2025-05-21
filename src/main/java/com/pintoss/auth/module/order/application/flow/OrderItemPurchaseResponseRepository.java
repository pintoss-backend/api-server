package com.pintoss.auth.module.order.application.flow;

import com.pintoss.auth.module.order.application.model.OrderItemPurchaseResponse;

public interface OrderItemPurchaseResponseRepository {

    OrderItemPurchaseResponse save(OrderItemPurchaseResponse orderItemPurchaseResponse);
}
