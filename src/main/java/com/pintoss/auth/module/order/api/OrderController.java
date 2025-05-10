package com.pintoss.auth.module.order.api;

import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.module.order.api.dto.CreateOrderRequest;
import com.pintoss.auth.module.order.api.dto.CreateOrderResponse;
import com.pintoss.auth.module.order.application.CancelOrderService;
import com.pintoss.auth.module.order.application.CreateOrderService;
import com.pintoss.auth.module.order.application.OrderQueryService;
import com.pintoss.auth.module.order.application.model.Order;
import com.pintoss.auth.module.order.application.model.OrderDetail;
import com.pintoss.auth.module.order.application.model.OrderSummary;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderService createOrderService;
    private final CancelOrderService cancelOrderService;
    private final OrderQueryService orderQueryService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{orderId}")
    public ApiResponse<OrderDetail> getOrderDetail(@PathVariable("orderId") Long orderId) {
        OrderDetail orderDetail = orderQueryService.getOrderDetail(orderId);
        // TODO : API 응답 객체로 변환 처리
        return ApiResponse.ok(orderDetail);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping()
    public ApiResponse<List<OrderSummary>> getMyOrderList() {
        List<OrderSummary> response = orderQueryService.getMyOrderList();
        // TODO : API 응답 객체로 변환 처리
        return ApiResponse.ok(response);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ApiResponse<CreateOrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest request) {
        Order saveOrder = createOrderService.create(request.getPaymentMethod(), request.getOrderItems());

        CreateOrderResponse response = CreateOrderResponse.builder()
            .serviceId("glx_api")
            .productName(saveOrder.getOrderName())
            .orderNo(saveOrder.getOrderNo())
            .ordererId(saveOrder.getOrdererId())
            .ordererName(saveOrder.getOrdererName())
            .ordererEmail(saveOrder.getOrdererEmail())
            .ordererPhone(saveOrder.getOrdererPhone())
            .serviceCode(saveOrder.getPaymentMethodType().getServiceCode())
            .price(saveOrder.getTotalPrice())
            .orderDate(saveOrder.getCreatedAt())
            .build();
        return ApiResponse.ok(response);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{orderNo}/cancel")
    public ApiResponse<Void> cancelOrder(@PathVariable("orderNo") String orderNo) {
        cancelOrderService.cancel(orderNo);

        return ApiResponse.ok(null);
    }
}
