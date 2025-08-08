package com.pintoss.auth.core.voucher.application.dto;


import com.pintoss.auth.core.voucher.domain.VoucherIssuerPaymentMethodType;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterVoucherIssuerPaymentMethodCommand {

    private Long voucherIssuerId;

    private List<NewVoucherIssuerPaymentDiscount> paymentMethods;

    public List<VoucherIssuerPaymentMethodType> getPaymentMethodTypes() {
        return paymentMethods.stream()
            .map(NewVoucherIssuerPaymentDiscount::getPaymentMethodType)
            .toList();
    }

    @Getter
    public static class NewVoucherIssuerPaymentDiscount {
        private VoucherIssuerPaymentMethodType paymentMethodType;
        private BigDecimal discountRate;

        public NewVoucherIssuerPaymentDiscount(VoucherIssuerPaymentMethodType paymentMethodType, BigDecimal discountRate) {
            this.paymentMethodType = paymentMethodType;
            this.discountRate = discountRate;
        }
    }

}
