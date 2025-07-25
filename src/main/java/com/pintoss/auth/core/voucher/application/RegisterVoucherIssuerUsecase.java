package com.pintoss.auth.core.voucher.application;

import com.pintoss.auth.core.voucher.application.dto.RegisterVoucherIssuerCommand;
import com.pintoss.auth.core.voucher.application.flow.reader.VoucherIssuerValidator;
import com.pintoss.auth.core.voucher.application.flow.writer.VoucherAdder;
import com.pintoss.auth.core.voucher.application.flow.writer.VoucherIssuerAdder;
import com.pintoss.auth.core.voucher.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterVoucherIssuerUsecase {

    private final VoucherIssuerValidator voucherIssuerValidator;
    private final VoucherIssuerAdder voucherIssuerAdder;
    private final VoucherAdder voucherAdder;

    @Transactional
    public void register(RegisterVoucherIssuerCommand command) {
        voucherIssuerValidator.validateIssuerNameDuplicate(command.getName());

        VoucherIssuer voucherIssuer = addIssuer(command);

        addVouchers(command, voucherIssuer);
    }

    private VoucherIssuer addIssuer(RegisterVoucherIssuerCommand command) {
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
        return voucherIssuer;
    }

    private void addVouchers(RegisterVoucherIssuerCommand command, VoucherIssuer voucherIssuer) {
        List<Voucher> vouchers = command.getVouchers().stream().map(voucher -> Voucher.create(
                voucherIssuer.getId(),
                voucher.getName(),
                voucherIssuer.getName(),
                voucher.getPrice()
        )).toList();
        voucherAdder.addAll(vouchers);
    }

}
