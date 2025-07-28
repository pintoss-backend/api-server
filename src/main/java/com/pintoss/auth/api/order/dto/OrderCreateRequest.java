package com.pintoss.auth.api.order.dto;

import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import com.pintoss.auth.core.order.application.dto.OrderItemRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {

    @NotEmpty(message = "주문 항목을 한 개 이상 포함해야 합니다.")
    @Valid
    private List<OrderItemRequest> orderItems;

}
