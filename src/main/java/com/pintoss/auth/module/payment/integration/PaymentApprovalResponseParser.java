package com.pintoss.auth.module.payment.integration;

public class PaymentApprovalResponseParser {
    public static PaymentApprovalResponse parse(String logString) {
        PaymentApprovalResponse response = new PaymentApprovalResponse();
        String[] tokens = logString.split(" ");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            int startIdx = token.indexOf('[');
            int endIdx = token.indexOf(']');

            if (startIdx > 0 && endIdx > startIdx) {
                String key = token.substring(0, startIdx);
                String value = token.substring(startIdx + 1, endIdx);
                value = trimTrailingComma(value); // ← 쉼표 제거 추가
                switch (key) {
                    case "version": response.setVersion(value); break;
                    case "mid": response.setMid(value); break;
                    case "serviceCode": response.setServiceCode(value); break;
                    case "command": response.setCommand(value); break;
                    case "orderId": response.setOrderId(value); break;
                    case "orderDate": response.setOrderDate(value); break;
                    case "numberOfRecord": response.setNumberOfRecord(value); break;
                    default: response.putField(key, value); break;
                }
            }
        }

        return response;
    }
    private static String trimTrailingComma(String value) {
        if (value != null && value.endsWith(",")) {
            return value.substring(0, value.length() - 1);
        }
        return value;
    }
}
