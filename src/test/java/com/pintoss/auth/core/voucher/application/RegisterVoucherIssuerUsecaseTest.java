package com.pintoss.auth.core.voucher.application;

import com.pintoss.auth.core.exception.CoreErrorCode;
import com.pintoss.auth.core.exception.CoreException;
import com.pintoss.auth.core.voucher.application.dto.RegisterVoucherIssuerCommand;
import com.pintoss.auth.core.voucher.application.flow.reader.VoucherIssuerValidator;
import com.pintoss.auth.core.voucher.application.flow.writer.VoucherAdder;
import com.pintoss.auth.core.voucher.application.flow.writer.VoucherIssuerAdder;
import com.pintoss.auth.core.voucher.domain.VoucherIssuer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterVoucherIssuerUsecaseTest {

    @Mock
    VoucherIssuerValidator validator;

    @Mock
    VoucherIssuerAdder issuerAdder;

    @Mock
    VoucherAdder voucherAdder;

    @InjectMocks
    RegisterVoucherIssuerUsecase usecase;

    @Test
    @DisplayName("상품권 발급자를 등록할 때 상품권 발급자와 상품권이 정상적으로 등록된다")
    void givenRegisterCommand_whenRegister_thenIssuerAndVouchersAreAdded() {
        // given
        RegisterVoucherIssuerCommand.RegisterVoucherCommand voucher1 = RegisterVoucherIssuerCommand.RegisterVoucherCommand.builder()
                .name("Test Voucher")
                .price(10000L)
                .quantity(100)
                .build();

        RegisterVoucherIssuerCommand.RegisterVoucherCommand voucher2 = RegisterVoucherIssuerCommand.RegisterVoucherCommand.builder()
                .name("Test Voucher 2")
                .price(20000L)
                .quantity(50)
                .build();

        RegisterVoucherIssuerCommand registerVoucherIssuerCommand = RegisterVoucherIssuerCommand.builder()
                .name("Test Issuer")
                .code("TEST_ISSUER")
                .cardDiscount(new BigDecimal(0))
                .phoneDiscount(new BigDecimal(0))
                .homePage("https://test-issuer.com")
                .csCenter("123-456-7890")
                .description("Test Issuer Description")
                .publisher("Test Publisher")
                .imageUrl("http://test-issuer.com/image.png")
                .note("Test Note")
                .fee(new BigDecimal(0))
                .vouchers(List.of(voucher1, voucher2))
                .build();

        // when
        usecase.register(registerVoucherIssuerCommand);

        // then
        verify(issuerAdder).add(any(VoucherIssuer.class));
        verify(voucherAdder).addAll(anyList());
    }

    @Test
    @DisplayName("상품권 발급자를 등록할 때 중복된 이름이 있으면 예외가 발생한다")
    void givenRegisterCommand_whenRegisterWithDuplicateName_thenExceptionIsThrown() {
        // given
        RegisterVoucherIssuerCommand registerVoucherIssuerCommand = RegisterVoucherIssuerCommand.builder()
                .name("Duplicate Issuer")
                .code("DUPLICATE_ISSUER")
                .cardDiscount(new BigDecimal(0))
                .phoneDiscount(new BigDecimal(0))
                .homePage("https://duplicate-issuer.com")
                .csCenter("123-456-7890")
                .description("Duplicate Issuer Description")
                .publisher("Test Publisher")
                .imageUrl("http://duplicate-issuer.com/image.png")
                .note("Test Note")
                .fee(new BigDecimal(0))
                .vouchers(List.of())
                .build();

        doThrow(new CoreException(CoreErrorCode.VOUCHER_ISSUER_ALREADY_EXISTS))
                .when(validator)
                .validateIssuerNameDuplicate(any());

        // when & then
        assertThatThrownBy(() -> usecase.register(registerVoucherIssuerCommand))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(CoreErrorCode.VOUCHER_ISSUER_ALREADY_EXISTS.getMessage());

        verify(issuerAdder, never()).add(any(VoucherIssuer.class));
        verify(voucherAdder, never()).addAll(anyList());
    }

}
