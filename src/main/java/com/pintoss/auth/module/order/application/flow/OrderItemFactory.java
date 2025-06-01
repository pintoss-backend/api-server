package com.pintoss.auth.module.order.application.flow;

import com.pintoss.auth.module.order.domain.OrderItem;
import com.pintoss.auth.module.order.domain.OrderItemRequest;
import com.pintoss.auth.module.voucher.model.Voucher;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderItemFactory {
    public static List<OrderItem> create(List<Voucher> vouchers, List<OrderItemRequest> orderItemRequests){
        Map<Long, Voucher> voucherMap = vouchers.stream()
            .collect(Collectors.toMap(Voucher::getId, Function.identity()));

        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItemRequest request: orderItemRequests) {
            Voucher voucher = voucherMap.get(request.getVoucherId());

            for (int i = 0; i < request.getQuantity(); i++) {
                orderItems.add(OrderItem.create(
                    voucher.getIssuerName(),
                    voucher.getName(),
                    request.getPrice()
                ));
            }
        }

        return orderItems;
    }
}
