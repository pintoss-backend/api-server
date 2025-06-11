package com.pintoss.auth.module.order.integration;

import com.pintoss.auth.common.security.SecurityContextUtils;
import com.pintoss.auth.module.payment.application.PaymentMethodType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PurchaseRequestBuilder {

    public static String buildHeader(String orderId) {
        StringBuilder sb = new StringBuilder();//M2483583

        sb.append(fixed("1001", 4));                    // COMMAND
        sb.append(fixed(orderId, 64));           // ORDER_ID
        sb.append(fixed("80000089", 8));                // CVS_ID
        sb.append(fixed("80000089", 8));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String now = LocalDateTime.now().format(formatter);// SUB_ID
        sb.append(fixed(now, 14));         // ORDER_DATE
        return sb.toString(); // 98 bytes
    }

    public static String buildBody(String orderId, String transId, String mid, String amount, PaymentMethodType paymentMethodType, String salePrice, String productCode) {
        StringBuilder sb = new StringBuilder();
        sb.append(fixed(productCode, 48));            // ITEM_CODE
        sb.append(fixed(salePrice, 8));                   // SALE_PRICE
        sb.append(fixed("", 8));                        // SETTLE_DAY
        sb.append(fixed(SecurityContextUtils.getPhone(), 12));            // BUY_HPNO
        sb.append(fixed(SecurityContextUtils.getPhone(), 12));                       // HPNO
        sb.append(fixed("", 6));                        // OTP_NO
        sb.append(fixed("", 20));                       // PIN_NO
        sb.append(fixed(mid, 20));            // BILLGATE_MID
        sb.append(fixed(paymentMethodType == PaymentMethodType.CARD ? "02" : "03", 2));                      // PG_METHOD
        sb.append(fixed(orderId, 64));           // PG_ORDER_ID
        sb.append(fixed(transId, 20));          // PG_TRID
        sb.append(fixed(amount, 8));                   // PG_PAY_PRICE
        sb.append(fixed("", 186));                      // RESERVED

        return sb.toString();
    }
    private static String fixed(String value, int length) {
        if (value == null) value = "";
        return String.format("%-" + length + "s", value).substring(0, length);
    }
}



