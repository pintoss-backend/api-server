package com.pintoss.auth.module.order.api;

import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.module.order.api.dto.CreateOrderRequest;
import com.pintoss.auth.module.order.api.dto.CreateOrderResponse;
import com.pintoss.auth.module.order.usecase.CancelOrderService;
import com.pintoss.auth.module.order.usecase.CreateOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ApiResponse<CreateOrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest request) {
        CreateOrderResponse response = createOrderService.create(request.getPaymentMethod(), request.getOrderItems());



        return ApiResponse.ok(response);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{orderNo}/cancel")
    public ApiResponse<Void> cancelOrder(@PathVariable("orderNo") String orderNo) {
        cancelOrderService.cancel(orderNo);

        return ApiResponse.ok(null);
    }
}
