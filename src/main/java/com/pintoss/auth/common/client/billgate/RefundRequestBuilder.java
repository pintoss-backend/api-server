package com.pintoss.auth.common.client.billgate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RefundRequestBuilder {

    public static String buildHeader(String orderId) {
        StringBuilder sb = new StringBuilder();//M2483583

        sb.append(fixed("2001", 4));                    // COMMAND
        sb.append(fixed(orderId, 64));           // ORDER_ID
        sb.append(fixed("80000089", 8));                // CVS_ID
        sb.append(fixed("80000089", 8));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String now = LocalDateTime.now().format(formatter);// SUB_ID
        sb.append(fixed(now, 14));         // ORDER_DATE
        return sb.toString(); // 98 bytes
    }

    public static String buildBody(String approvalNo) {
        StringBuilder sb = new StringBuilder();
        sb.append(fixed(approvalNo, 32));            // ITEM_CODE         // PG_PAY_PRICE
        sb.append(fixed("", 382));                      // RESERVED

        return sb.toString();
    }

    private static String fixed(String value, int length) {
        if (value == null) value = "";
        return String.format("%-" + length + "s", value).substring(0, length);
    }

}
