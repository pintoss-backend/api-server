package com.pintoss.auth.core.order.application.flow.validator;

import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.api.support.exception.client.BadRequestException;
import com.pintoss.auth.core.order.application.dto.OrderItemRequest;
import com.pintoss.auth.core.order.application.repository.OrderRepository;
import com.pintoss.auth.core.order.domain.Order;
import com.pintoss.auth.core.voucher.domain.Voucher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public Order getOrThrowIfNotExists(String orderNo){
        return orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ORDER_NOT_FOUND));
    }
}
