package com.pintoss.auth.api.order;

import com.galaxia.api.util.ChecksumUtil;
import com.pintoss.auth.api.order.query.OrderQueryService;
import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.common.logging.LogContext;
import com.pintoss.auth.common.paging.PageResponse;
import com.pintoss.auth.common.paging.PagedData;
import com.pintoss.auth.common.security.SecurityContextUtils;
import com.pintoss.auth.core.order.application.OrderCancelService;
import com.pintoss.auth.core.order.application.OrderCreateService;
import com.pintoss.auth.core.order.application.OrderRefundService;
import com.pintoss.auth.core.order.domain.Order;
import com.pintoss.auth.core.order.domain.OrderDetail;
import com.pintoss.auth.core.order.domain.OrderSearchResult;
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
    private final OrderQueryService orderQueryService;
    private final OrderCreateService orderCreateService;
    private final OrderCancelService orderCancelService;
    private final OrderRefundService orderRefundService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{orderId}")
    public ApiResponse<OrderDetail> getOrderDetail(@PathVariable("orderId") Long orderId) {
        OrderDetail response = orderQueryService.getOrderDetail(orderId);
        return ApiResponse.ok(response);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping()
    public ApiResponse<PageResponse<OrderSearchResult>> getMyOrderList(@ModelAttribute OrderPageRequest request) {
        PagedData<OrderSearchResult> response = orderQueryService.getMyOrderList(request.to());

        return ApiResponse.ok(new PageResponse(response.getItems(), request.getPage(), request.getSize(),response.getTotalElements()));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ApiResponse<OrderCreateResponse> createOrder(@RequestBody @Valid OrderCreateRequest request) {
        Order saveOrder = orderCreateService.create(request.getOrderItems(), request.getPaymentMethod());

        LogContext.putOrder(saveOrder.getOrderNo());
        log.info("[주문 생성]");
        OrderCreateResponse response = null;
        try {
            response = OrderCreateResponse.builder()
                .serviceId(serviceId)
                .productName(saveOrder.getOrderName())
                .orderNo(saveOrder.getOrderNo())
                .ordererId(saveOrder.getOrdererId())
                .ordererName(saveOrder.getOrdererName())
                .ordererEmail(saveOrder.getOrdererEmail())
                .ordererPhone(saveOrder.getOrdererPhone())
                .serviceCode(request.getPaymentMethod().getServiceCode())
                .price(saveOrder.getTotalPrice())
                .orderDate(saveOrder.getCreatedAt())
                .checkSum(
                    ChecksumUtil.genCheckSum(
                        serviceId+
                            saveOrder.getOrderNo()+
                            saveOrder.getTotalPrice()
                    )
                )
                .checkSumHp(ChecksumUtil.genCheckSum(
                    SecurityContextUtils.getPhone()
                ))
                .build();
        } catch (Exception e) {
            throw new BadRequestException(ErrorCode.BILLGATE_CHECHKSUM_GENERATION_FAILED);
        }
        return ApiResponse.ok(response);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{orderNo}/cancel")
    public ApiResponse<Void> cancelOrder(@PathVariable("orderNo") String orderNo) {
        orderCancelService.cancel(orderNo);
        return ApiResponse.ok(null);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{orderNo}/refund")
    public ApiResponse<Void> refundOrder(@PathVariable("orderNo") String orderNo) {
        orderRefundService.refund(orderNo);
        return ApiResponse.ok(null);
    }
}
