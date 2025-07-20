package com.pintoss.auth.core.voucher.application.flow.reader;

import com.pintoss.auth.core.exception.CoreErrorCode;
import com.pintoss.auth.core.exception.CoreException;
import com.pintoss.auth.core.voucher.application.repository.VoucherIssuerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoucherIssuerValidatorTest {

    @Mock
    VoucherIssuerRepository voucherIssuerRepository;

    @InjectMocks
    VoucherIssuerValidator voucherIssuerValidator;

    @Test
    @DisplayName("상품권 발행처 이름이 중복된다면 예외가 발생한다")
    void validateIssuerNameDuplicate() {
        // given
        when(voucherIssuerRepository.existsByName("중복된 발행처 이름"))
                .thenReturn(true);

        // when & then
        assertThatThrownBy(() ->
                voucherIssuerValidator.validateIssuerNameDuplicate("중복된 발행처 이름"))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(CoreErrorCode.VOUCHER_ISSUER_ALREADY_EXISTS.getMessage());
    }

    @Test
    @DisplayName("상품권 발행처 이름이 중복되지 않는다면 예외가 발생하지 않는다")
    void validateIssuerNameNotDuplicate() {
        // given
        when(voucherIssuerRepository.existsByName("유일한 발행처 이름"))
                .thenReturn(false);

        // when & then
        voucherIssuerValidator.validateIssuerNameDuplicate("유일한 발행처 이름");
    }
}
