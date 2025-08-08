package com.pintoss.auth.core.voucher.application.flow.writer;

import com.pintoss.auth.core.voucher.application.dto.RegisterVoucherIssuerPaymentMethodCommand.NewVoucherIssuerPaymentDiscount;
import com.pintoss.auth.core.voucher.domain.VoucherIssuerPaymentMethod;
import com.pintoss.auth.core.voucher.domain.VoucherIssuerPaymentMethods;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VoucherIssuerPaymentMethodFactory {

    public VoucherIssuerPaymentMethods create(Long voucherIssuerId, List<NewVoucherIssuerPaymentDiscount> newDiscounts) {
        List<VoucherIssuerPaymentMethod> voucherIssuerPaymentMethods = newDiscounts.stream().map(
            newDiscount -> new VoucherIssuerPaymentMethod(
                voucherIssuerId,
                newDiscount.getPaymentMethodType(),
                newDiscount.getDiscountRate()
            )
        ).toList();
        return new VoucherIssuerPaymentMethods(voucherIssuerPaymentMethods);
    }

}
