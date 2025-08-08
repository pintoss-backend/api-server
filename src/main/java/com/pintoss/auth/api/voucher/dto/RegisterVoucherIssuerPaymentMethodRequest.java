package com.pintoss.auth.api.voucher.dto;

import com.pintoss.auth.core.voucher.application.dto.RegisterVoucherIssuerPaymentMethodCommand;
import com.pintoss.auth.core.voucher.application.dto.RegisterVoucherIssuerPaymentMethodCommand.NewVoucherIssuerPaymentDiscount;
import com.pintoss.auth.core.voucher.domain.VoucherIssuerPaymentMethodType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterVoucherIssuerPaymentMethodRequest {

    @NotNull(message = "할인 정보는 필수 항목입니다.")
    @Valid
    private List<PaymentMethodRequest> paymentMethods;

    public RegisterVoucherIssuerPaymentMethodCommand to(Long voucherIssuerId) {
        List<NewVoucherIssuerPaymentDiscount> newDiscounts = paymentMethods.stream()
            .map(discount ->
                new NewVoucherIssuerPaymentDiscount(
                    discount.getPaymentMethodType(),
                    discount.getDiscountRate()
                )).toList();
        return new RegisterVoucherIssuerPaymentMethodCommand(voucherIssuerId, newDiscounts);
    }

    @Getter
    public static class PaymentMethodRequest {
        @NotNull
        private VoucherIssuerPaymentMethodType paymentMethodType;
        @Min(0)
        @Max(100)
        private BigDecimal discountRate;
    }

}
