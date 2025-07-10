package com.pintoss.auth.core.order.application;

import com.pintoss.auth.api.security.SecurityContextUtils;
import com.pintoss.auth.core.order.application.dto.OrderCreateResult;
import com.pintoss.auth.core.order.application.flow.writer.OrderAdder;
import com.pintoss.auth.core.order.application.flow.processor.OrderItemFactory;
import com.pintoss.auth.core.order.application.flow.validator.OrderValidator;
import com.pintoss.auth.core.order.domain.Order;
import com.pintoss.auth.core.order.domain.OrderItem;
import com.pintoss.auth.core.order.application.dto.OrderItemRequest;
import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import com.pintoss.auth.core.voucher.domain.Voucher;
import com.pintoss.auth.core.voucher.application.flow.reader.VoucherReader;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCreateUsecase {

    private final VoucherReader voucherReader;
    private final OrderAdder orderAdder;
    private final OrderValidator orderValidator;

    public OrderCreateResult create(List<OrderItemRequest> orderItemRequest, PaymentMethodType paymentMethod) {

        // 1. 상품권 조회
        List<Voucher> vouchers = voucherReader.getAll(orderItemRequest.stream()
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
        orderAdder.add(order);

        return OrderCreateResult.of(
            order.getOrderNo(),
            order.getOrderName(),
            order.getTotalPrice(),
            order.getCreatedAt(),
            order.getStatus()
        );
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
