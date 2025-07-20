package com.pintoss.auth.client.galaxia;

import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import com.pintoss.auth.core.voucher.application.dto.VoucherPurchaseCommand;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PurchaseRequestBuilder {

    private static final String DATE_FORMAT = "yyyyMMddHHmmss";

    public static String buildHeader(String orderId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String now = LocalDateTime.now().format(formatter);                 // SUB_ID

        return new StringBuilder()                                           //M2483583
                .append(fixed("1001", 4))                    // COMMAND
                .append(fixed(orderId, 64))                         // ORDER_ID
                .append(fixed("80000089", 8))                // CVS_ID
                .append(fixed("80000089", 8))
                .append(fixed(now, 14))                            // ORDER_DATE
                .toString();                                               // 98 bytes
    }

    public static String buildBody(VoucherPurchaseCommand command) {
        return new StringBuilder()
                .append(fixed(command.getProductCode(), 48))                                                   // ITEM_CODE
                .append(fixed(command.getSalePrice().toString(), 8))                                           // SALE_PRICE
                .append(fixed("", 8))                                                                   // SETTLE_DAY
                .append(fixed("", 12))                                                                  // BUY_HPNO
                .append(fixed("", 12))                                                                  // HPNO
                .append(fixed("", 6))                                                                   // OTP_NO
                .append(fixed("", 20))                                                                  // PIN_NO
                .append(fixed(command.getMId(), 20))                                                           // BILLGATE_MID
                .append(fixed(command.getPaymentMethodType() == PaymentMethodType.CARD ? "02" : "03", 2))      // PG_METHOD
                .append(fixed(command.getOrderNo(), 64))                                                       // PG_ORDER_ID
                .append(fixed(command.getTransactionId(), 20))                                                 // PG_TRID
                .append(fixed(command.getSalePrice().toString(), 8))                                           // PG_PAY_PRICE
                .append(fixed("", 186))                                                                // RESERVED
                .toString();
    }

    private static String fixed(String value, int length) {
        if (value == null) value = "";
        return String.format("%-" + length + "s", value).substring(0, length);
    }
}



