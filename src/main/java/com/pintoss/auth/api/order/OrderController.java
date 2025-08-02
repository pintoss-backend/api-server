package com.pintoss.auth.api.order;

import com.pintoss.auth.api.order.dto.OrderCreateRequest;
import com.pintoss.auth.api.order.dto.OrderDetailResponse;
import com.pintoss.auth.api.order.dto.OrderPageRequest;
import com.pintoss.auth.api.support.dto.ApiResponse;
import com.pintoss.auth.api.support.interceptor.AuthorizationRequired;
import com.pintoss.auth.storage.user.jpa.entity.UserRoleEnum;
import com.pintoss.auth.support.logging.LogContext;
import com.pintoss.auth.api.support.paging.PageResponse;
import com.pintoss.auth.api.support.paging.PagedData;
import com.pintoss.auth.core.order.application.GetMyOrdersUsecase;
import com.pintoss.auth.core.order.application.GetOrderDetailUsecase;
import com.pintoss.auth.core.order.application.OrderCancelUsecase;
import com.pintoss.auth.core.order.application.OrderCreateUsecase;
import com.pintoss.auth.core.order.application.dto.OrderDetail;
import com.pintoss.auth.core.order.application.dto.OrderSearchResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final GetMyOrdersUsecase getMyOrdersUsecase;
    private final GetOrderDetailUsecase getOrderDetailUsecase;
    private final OrderCreateUsecase orderCreateUsecase;
    private final OrderCancelUsecase orderCancelUseCase;

    @AuthorizationRequired(value = UserRoleEnum.USER)
    @GetMapping("/{orderId}")
    public ApiResponse<OrderDetailResponse> getOrderDetail(@PathVariable("orderId") Long orderId) {
        OrderDetail response = getOrderDetailUsecase.getOrderDetail(orderId);
        return ApiResponse.ok(OrderDetailResponse.from(response));
    }

    @AuthorizationRequired(value = UserRoleEnum.USER)
    @GetMapping()
    public ApiResponse<PageResponse<OrderSearchResult>> getMyOrderList(@ModelAttribute OrderPageRequest request) {
        PagedData<OrderSearchResult> response = getMyOrdersUsecase.getMyOrders(request.to());

        return ApiResponse.ok(new PageResponse(response.getItems(), request.getPage(), request.getSize(),response.getTotalElements()));
    }

    @AuthorizationRequired(value = UserRoleEnum.USER)
    @PostMapping
    public ApiResponse<String> createOrder(@RequestBody @Valid OrderCreateRequest request) {
        String orderNo = orderCreateUsecase.create(request.getOrderItems());

        LogContext.putOrder(orderNo);
        log.info("[주문 생성]");

        return ApiResponse.ok(orderNo);
    }

    @AuthorizationRequired(value = UserRoleEnum.USER)
    @PutMapping("/{orderNo}/cancel")
    public ApiResponse<Void> cancelOrder(@PathVariable("orderNo") String orderNo) {
        orderCancelUseCase.cancel(orderNo);
        return ApiResponse.ok(null);
    }

    @AuthorizationRequired(value = UserRoleEnum.USER)
    @PostMapping("/{orderNo}/refund")
    public ApiResponse<Void> refundOrder(@PathVariable("orderNo") String orderNo) {
        return ApiResponse.ok(null);
    }
}
