package com.pintoss.auth.module.voucher.usecase;

import com.pintoss.auth.module.voucher.api.dto.RegisterVoucherIssuerRequest;
import com.pintoss.auth.module.voucher.execution.VoucherAdder;
import com.pintoss.auth.module.voucher.execution.VoucherIssuerAdder;
import com.pintoss.auth.module.voucher.execution.VoucherIssuerValidator;
import com.pintoss.auth.module.voucher.execution.domain.ContactInfo;
import com.pintoss.auth.module.voucher.execution.domain.CsCenter;
import com.pintoss.auth.module.voucher.execution.domain.Discount;
import com.pintoss.auth.module.voucher.execution.domain.HomePage;
import com.pintoss.auth.module.voucher.execution.domain.Voucher;
import com.pintoss.auth.module.voucher.execution.domain.VoucherIssuer;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterVoucherIssuerUseCase {

    private final VoucherIssuerValidator voucherIssuerValidator;
    private final VoucherIssuerAdder voucherIssuerAdder;
    private final VoucherAdder voucherAdder;

    public void register(RegisterVoucherIssuerRequest request) {
        voucherIssuerValidator.isIssuerNameDuplicated(request.getName());

        VoucherIssuer voucherIssuer = VoucherIssuer.create(
            request.getName(),
            request.getCode(),
            new Discount(request.getCardDiscount(), request.getPhoneDiscount()),
            new ContactInfo(
                new HomePage(request.getHomePage()),
                new CsCenter(request.getCsCenter())
            ),
            request.getDescription(),
            request.getPublisher(),
            request.getNote(),
            request.getImageUrl()
        );
        voucherIssuerAdder.add(voucherIssuer);

        List<Voucher> vouchers = request.getVouchers().stream().map(v -> {
            return Voucher.create(
                voucherIssuer.getId(),
                v.getName(),
                voucherIssuer.getName(),
                v.getPrice()
            );
        }).collect(Collectors.toList());
        voucherAdder.add(vouchers);
    }
}
