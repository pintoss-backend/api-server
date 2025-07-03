package com.pintoss.auth.core.order.application.flow;

import com.pintoss.auth.core.order.domain.OrderItemRequest;
import com.pintoss.auth.core.voucher.model.Voucher;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    private final OrderRepository orderRepository;

    public void validateOrderItems(List<OrderItemRequest> orderItems, List<Voucher> vouchers) {
        Map<Long, Voucher> voucherMap = vouchers.stream()
            .collect(Collectors.toMap(Voucher::getId, Function.identity()));

        orderItems.stream()
            .forEach(orderItem -> {
                Voucher voucher = voucherMap.get(orderItem.getVoucherId());
                voucher.validatePrice(orderItem.getPrice());
            });
    }

}
