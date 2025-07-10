package com.pintoss.auth.api.order;

import com.galaxia.api.util.ChecksumUtil;
import com.pintoss.auth.api.order.dto.OrderCreateRequest;
import com.pintoss.auth.api.order.dto.OrderCreateResponse;
import com.pintoss.auth.api.order.dto.OrderDetailResponse;
import com.pintoss.auth.api.order.dto.OrderPageRequest;
import com.pintoss.auth.api.common.response.ApiResponse;
import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.common.logging.LogContext;
import com.pintoss.auth.api.common.response.PageResponse;
import com.pintoss.auth.api.common.paging.PagedData;
import com.pintoss.auth.api.security.SecurityContextUtils;
import com.pintoss.auth.core.order.application.GetMyOrdersUsecase;
import com.pintoss.auth.core.order.application.GetOrderDetailUsecase;
import com.pintoss.auth.core.order.application.OrderCancelUsecase;
import com.pintoss.auth.core.order.application.OrderCreateUsecase;
import com.pintoss.auth.core.order.application.dto.OrderCreateResult;
import com.pintoss.auth.core.order.domain.Order;
import com.pintoss.auth.core.order.application.dto.OrderDetail;
import com.pintoss.auth.core.order.application.dto.OrderSearchResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    @Value("${galaxia.service-id}")
    private String serviceId;
    private final GetMyOrdersUsecase getMyOrdersUsecase;
    private final GetOrderDetailUsecase getOrderDetailUsecase;
    private final OrderCreateUsecase orderCreateUsecase;
    private final OrderCancelUsecase orderCancelUseCase;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{orderId}")
    public ApiResponse<OrderDetailResponse> getOrderDetail(@PathVariable("orderId") Long orderId) {
        OrderDetail response = getOrderDetailUsecase.getOrderDetail(orderId);
        return ApiResponse.ok(OrderDetailResponse.from(response));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping()
    public ApiResponse<PageResponse<OrderSearchResult>> getMyOrderList(@ModelAttribute OrderPageRequest request) {
        PagedData<OrderSearchResult> response = getMyOrdersUsecase.getMyOrders(request.to());

        return ApiResponse.ok(new PageResponse(response.getItems(), request.getPage(), request.getSize(),response.getTotalElements()));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ApiResponse<OrderCreateResponse> createOrder(@RequestBody @Valid OrderCreateRequest request) {
        OrderCreateResult result = orderCreateUsecase.create(request.getOrderItems(), request.getPaymentMethod());

        LogContext.putOrder(result.getOrderNo());
        log.info("[주문 생성]");

        OrderCreateResponse response = OrderCreateResponse.of(serviceId, result, request.getPaymentMethod().getServiceCode());

        return ApiResponse.ok(response);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{orderNo}/cancel")
    public ApiResponse<Void> cancelOrder(@PathVariable("orderNo") String orderNo) {
        orderCancelUseCase.cancel(orderNo);
        return ApiResponse.ok(null);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{orderNo}/refund")
    public ApiResponse<Void> refundOrder(@PathVariable("orderNo") String orderNo) {
        return ApiResponse.ok(null);
    }
}
