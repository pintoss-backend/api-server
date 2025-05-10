package com.pintoss.auth.module.order.application;

import com.pintoss.auth.common.security.SecurityContextUtils;
import com.pintoss.auth.module.order.application.flow.OrderAdder;
import com.pintoss.auth.module.order.application.flow.OrderItemFactory;
import com.pintoss.auth.module.order.application.flow.OrderValidator;
import com.pintoss.auth.module.order.application.model.Order;
import com.pintoss.auth.module.order.application.model.OrderItem;
import com.pintoss.auth.module.order.application.model.OrderItemRequest;
import com.pintoss.auth.module.order.application.model.PaymentMethodType;
import com.pintoss.auth.module.voucher.model.Voucher;
import com.pintoss.auth.module.voucher.usecase.service.VoucherReader;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderService {

    private final VoucherReader voucherReader;
    private final OrderAdder orderAdder;
    private final OrderValidator orderValidator;

    public Order create(PaymentMethodType paymentMethod, List<OrderItemRequest> orderItemRequest) {

        // 1. 상품권 조회
        List<Voucher> vouchers = voucherReader.readAll(orderItemRequest.stream()
            .map(OrderItemRequest::getVoucherId)
            .toList());

        // 2. 가격 검증
        orderValidator.validateOrderItems(orderItemRequest, vouchers);

        // 3. 주문 상품 생성
        List<OrderItem> orderItems = OrderItemFactory.create(vouchers, orderItemRequest);

        // 4. 주문 생성
        Order order = Order.create(
            SecurityContextUtils.getUserId(),
            SecurityContextUtils.getName(),
            SecurityContextUtils.getEmail(),
            SecurityContextUtils.getPhone(),
            generateProductName(orderItems),
            orderItems,
            paymentMethod
        );

        // 5. 주문 저장
        return orderAdder.add(order);
    }

    private String generateProductName(List<OrderItem> orderItems) {
        int size = orderItems.stream().map(orderItem -> orderItem.getVoucherIssuerName()).collect(
            Collectors.toSet()).size();
        if (size == 1) {
            return orderItems.get(0).getVoucherIssuerName();
        }
        return orderItems.get(0).getVoucherIssuerName() + " 외 " + (size - 1) + "건";
    }
}
