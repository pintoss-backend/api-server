package com.pintoss.auth.api.payment.dto;

import com.pintoss.auth.core.payment.application.dto.PaymentCreateCommand;
import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCreateRequest {

    @NotNull(message = "주문 번호는 필수 입력 항목입니다.")
    private String orderNo;

    @NotNull(message = "결제 방법은 필수 입력 항목입니다.")
    private PaymentMethodType paymentMethod;

    public PaymentCreateCommand to() {
        return new PaymentCreateCommand(
                orderNo,
                paymentMethod
        );
    }
}
