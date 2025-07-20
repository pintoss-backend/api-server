package com.pintoss.auth.core.voucher.domain;

import com.pintoss.auth.api.support.exception.client.BadRequestException;
import com.pintoss.auth.support.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class VoucherTest {

    @Test
    @DisplayName("두 상품권의 가격이 다르다면 비교 시 예외가 발생한다")
    void givenDifferentPrices_whenCompareVouchers_thenThrowException() {
        // given
        Voucher voucher1 = Voucher.create(1000L, "상품권1", "발급자1", 1000L);
        Voucher voucher2 = Voucher.create(2000L, "상품권2", "발급자2", 2000L);

        // when & then
        assertThatThrownBy(() -> voucher1.validatePrice(voucher2.getPrice()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ErrorCode.VOUCHER_PRICE_MISMATCH.getMessage());
    }

    @Test
    @DisplayName("상품권의 가격이 일치하면 비교가 성공한다")
    void givenSamePrice_whenCompareVouchers_thenSuccess() {
        // given
        Voucher voucher1 = Voucher.create(1000L, "상품권1", "발급자1", 1000L);
        Voucher voucher2 = Voucher.create(1000L, "상품권2", "발급자2", 1000L);

        // when & then
        assertDoesNotThrow(() -> voucher1.validatePrice(voucher2.getPrice()));
    }

}
