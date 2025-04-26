package com.pintoss.auth.module.order.execution;

import com.pintoss.auth.module.order.execution.domain.OrderItem;
import com.pintoss.auth.module.order.usecase.dto.OrderItemRequest;
import com.pintoss.auth.module.voucher.execution.domain.Voucher;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderItemFactory {
    public static List<OrderItem> create(List<Voucher> vouchers, List<OrderItemRequest> orderItemRequests){
        Map<Long, Voucher> voucherMap = vouchers.stream()
            .collect(Collectors.toMap(Voucher::getId, Function.identity()));

        List<OrderItem> orderItems = orderItemRequests.stream().map(orderItemRequest -> {
            Voucher voucher = voucherMap.get(orderItemRequest.getVoucherId());
            return OrderItem.create(
                voucher.getIssuerName(),
                voucher.getName(),
                orderItemRequest.getQuantity(),
                orderItemRequest.getPrice()
            );
        }).collect(Collectors.toList());

        return orderItems;
    }
}
