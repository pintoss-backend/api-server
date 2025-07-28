package com.pintoss.auth.core.voucher.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import com.pintoss.auth.core.support.event.VoucherPurchaseCompletedEvent;
import com.pintoss.auth.core.voucher.application.dto.PurchaseResult;
import com.pintoss.auth.core.voucher.application.dto.VoucherPurchaseCommand;
import com.pintoss.auth.core.voucher.application.event.VoucherPurchaseCompletedEventPublisher;
import com.pintoss.auth.core.voucher.application.flow.external.Purchaser;
import com.pintoss.auth.support.exception.BaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VoucherPurchaseUsecaseTest {

    @Mock
    Purchaser purchaser;

    @Mock
    VoucherPurchaseCompletedEventPublisher eventPublisher;

    @InjectMocks
    VoucherPurchaseUsecase usecase;

    @Test
    @DisplayName("상품권을 구매하면 상품권 구매 이벤트가 발행된다")
    void givenPurchaseCommand_whenPurchase_thenEventIsPublished() {
        // given
        VoucherPurchaseCommand voucherPurchaseCommand = new VoucherPurchaseCommand(
                "orderNo",
                1L,
                "issuerId",
                "amount",
                10000L,
                1000L,
                PaymentMethodType.CARD,
                "productCode"
        );

        when(purchaser.purchase(voucherPurchaseCommand))
                .thenReturn(new PurchaseResult(true,
                        "1104501710200000",
                        "approvalCode",
                        "itemName",
                        9000L,
                        "message"));

        // when
         usecase.purchase(voucherPurchaseCommand);

        // then
         verify(eventPublisher).publish(any(VoucherPurchaseCompletedEvent.class));
    }

    @Test
    @DisplayName("상품권 구매 시도 중 예외가 발생하면 이벤트가 발행되지 않는다")
    void givenPurchaseCommand_whenPurchaseThrowsException_thenEventIsNotPublished() {
        // given
        VoucherPurchaseCommand voucherPurchaseCommand = new VoucherPurchaseCommand(
                "orderNo",
                1L,
                "issuerId",
                "amount",
                10000L,
                1000L,
                PaymentMethodType.CARD,
                "productCode"
        );

        when(purchaser.purchase(voucherPurchaseCommand))
                .thenThrow(new BaseException());

        // when & then
        assertThatThrownBy(() -> usecase.purchase(voucherPurchaseCommand))
                .isInstanceOf(BaseException.class);

        // verify that no event was published
        verify(eventPublisher, never()).publish(any(VoucherPurchaseCompletedEvent.class));
    }

}
