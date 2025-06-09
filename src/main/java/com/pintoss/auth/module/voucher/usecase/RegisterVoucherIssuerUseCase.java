package com.pintoss.auth.module.voucher.usecase;

import com.pintoss.auth.module.voucher.model.ContactInfo;
import com.pintoss.auth.module.voucher.model.CsCenter;
import com.pintoss.auth.module.voucher.model.Discount;
import com.pintoss.auth.module.voucher.model.HomePage;
import com.pintoss.auth.module.voucher.model.Voucher;
import com.pintoss.auth.module.voucher.model.VoucherIssuer;
import com.pintoss.auth.module.voucher.usecase.dto.RegisterVoucherIssuerCommand;
import com.pintoss.auth.module.voucher.usecase.service.VoucherAdder;
import com.pintoss.auth.module.voucher.usecase.service.VoucherIssuerAdder;
import com.pintoss.auth.module.voucher.usecase.service.VoucherIssuerValidator;
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

    public void register(RegisterVoucherIssuerCommand command) {
        voucherIssuerValidator.isIssuerNameDuplicated(command.getName());

        VoucherIssuer voucherIssuer = VoucherIssuer.create(
            command.getName(),
            command.getCode(),
            new Discount(command.getCardDiscount(), command.getPhoneDiscount()),
            new ContactInfo(
                new HomePage(command.getHomePage()),
                new CsCenter(command.getCsCenter())
            ),
            command.getDescription(),
            command.getPublisher(),
            command.getNote(),
            command.getImageUrl(),
            command.getFee()
        );
        voucherIssuerAdder.add(voucherIssuer);

        List<Voucher> vouchers = command.getVouchers().stream().map(v -> {
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
