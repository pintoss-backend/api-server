package com.pintoss.auth.core.voucher.application;

import com.pintoss.auth.core.voucher.application.dto.RegisterVoucherIssuerPaymentMethodCommand;
import com.pintoss.auth.core.voucher.application.flow.writer.VoucherIssuerPaymentMethodAdder;
import com.pintoss.auth.core.voucher.application.flow.writer.VoucherIssuerPaymentMethodFactory;
import com.pintoss.auth.core.voucher.application.flow.reader.VoucherIssuerPaymentMethodValidator;
import com.pintoss.auth.core.voucher.domain.VoucherIssuerPaymentMethods;
import com.pintoss.auth.core.voucher.application.flow.reader.VoucherIssuerReader;
import org.springframework.stereotype.Service;

@Service
public class RegisterVoucherIssuerPaymentMethodUsecase {

    private final VoucherIssuerReader voucherIssuerReader;
    private final VoucherIssuerPaymentMethodValidator voucherIssuerPaymentMethodValidator;
    private final VoucherIssuerPaymentMethodFactory voucherIssuerPaymentMethodFactory;
    private final VoucherIssuerPaymentMethodAdder voucherIssuerPaymentMethodAdder;

    public RegisterVoucherIssuerPaymentMethodUsecase(VoucherIssuerReader voucherIssuerReader,
        VoucherIssuerPaymentMethodValidator voucherIssuerPaymentMethodValidator,
        VoucherIssuerPaymentMethodFactory voucherIssuerPaymentMethodFactory,
        VoucherIssuerPaymentMethodAdder voucherIssuerPaymentMethodAdder) {
        this.voucherIssuerReader = voucherIssuerReader;
        this.voucherIssuerPaymentMethodValidator = voucherIssuerPaymentMethodValidator;
        this.voucherIssuerPaymentMethodFactory = voucherIssuerPaymentMethodFactory;
        this.voucherIssuerPaymentMethodAdder = voucherIssuerPaymentMethodAdder;
    }

    public void registerPaymentMethod(RegisterVoucherIssuerPaymentMethodCommand command) {
        // 1. 존재하는 상품권 발행사 인지 확인 한다.
        voucherIssuerReader.getOrElseThrow(
            command.getVoucherIssuerId());

        // 2. 이미 등록된 결제 수단 인지 확인 한다.
        voucherIssuerPaymentMethodValidator.checkAlreadyRegistered(
            command.getVoucherIssuerId(),
            command.getPaymentMethodTypes()
        );

        // 3. 상품권 발급자 결제 수단을 생성 한다.
        VoucherIssuerPaymentMethods paymentMethods = voucherIssuerPaymentMethodFactory.create(
            command.getVoucherIssuerId(),
            command.getPaymentMethods()
        );

        // 4. 저장 한다.
        voucherIssuerPaymentMethodAdder.saveAll(paymentMethods.getPaymentMethods());
    }
}
