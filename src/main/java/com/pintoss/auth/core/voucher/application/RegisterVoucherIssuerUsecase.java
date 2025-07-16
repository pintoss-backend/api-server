package com.pintoss.auth.core.voucher.application;

import com.pintoss.auth.storage.voucher.jpa.entity.ContactInfoEmbeddable;
import com.pintoss.auth.storage.voucher.jpa.entity.CsCenterEmbeddable;
import com.pintoss.auth.storage.voucher.jpa.entity.DiscountEmbeddable;
import com.pintoss.auth.storage.voucher.jpa.entity.HomePageEmbeddable;
import com.pintoss.auth.core.voucher.domain.Voucher;
import com.pintoss.auth.core.voucher.domain.VoucherIssuer;
import com.pintoss.auth.core.voucher.application.dto.RegisterVoucherIssuerCommand;
import com.pintoss.auth.core.voucher.application.flow.writer.VoucherAdder;
import com.pintoss.auth.core.voucher.application.flow.writer.VoucherIssuerAdder;
import com.pintoss.auth.core.voucher.application.flow.validator.VoucherIssuerValidator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterVoucherIssuerUsecase {

    private final VoucherIssuerValidator voucherIssuerValidator;
    private final VoucherIssuerAdder voucherIssuerAdder;
    private final VoucherAdder voucherAdder;

    public void register(RegisterVoucherIssuerCommand command) {
        voucherIssuerValidator.isIssuerNameDuplicated(command.getName());

        VoucherIssuer voucherIssuer = VoucherIssuer.create(
            command.getName(),
            command.getCode(),
            new DiscountEmbeddable(command.getCardDiscount(), command.getPhoneDiscount()),
            new ContactInfoEmbeddable(
                new HomePageEmbeddable(command.getHomePage()),
                new CsCenterEmbeddable(command.getCsCenter())
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
