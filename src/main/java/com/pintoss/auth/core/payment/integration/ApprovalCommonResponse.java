package com.pintoss.auth.core.payment.integration;

import lombok.Data;

@Data
public class ApprovalCommonResponse {
    private Boolean isSuccess;

    private String version;
    private String mid;
    private String serviceCode;
    private String command;
    private String orderId;
    private String orderDate;

    private String transactionId;        // 1001
    private String responseCode;         // 1002
    private String responseMessage;      // 1003
    private String detailResponseCode;   // 1009
    private String detailResponseMessage;// 1010

    private String authDate;             // 1005
    private String authAmount;           // 1007
    private String eventUse;             // 5029
    private String eventDiscountAmount;  // 5030
    private String eventDemandAmount;    // 5031

    public ApprovalCommonResponse(Boolean isSuccess, String version, String mid, String serviceCode,
        String command, String orderId, String orderDate, String transactionId, String responseCode,
        String responseMessage, String detailResponseCode, String detailResponseMessage,
        String authDate, String authAmount, String eventUse, String eventDiscountAmount,
        String eventDemandAmount) {
        this.isSuccess = isSuccess;
        this.version = version;
        this.mid = mid;
        this.serviceCode = serviceCode;
        this.command = command;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.transactionId = transactionId;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.detailResponseCode = detailResponseCode;
        this.detailResponseMessage = detailResponseMessage;
        this.authDate = authDate;
        this.authAmount = authAmount;
        this.eventUse = eventUse;
        this.eventDiscountAmount = eventDiscountAmount;
        this.eventDemandAmount = eventDemandAmount;
    }

    public static ApprovalCommonResponse from (String version, String mid, String serviceCode,
        String command,
        String orderId, String orderDate, String transactionId, String responseCode,
        String responseMessage, String detailResponseCode, String detailResponseMessage,
        String authDate, String authAmount, String eventUse, String eventDiscountAmount,
        String eventDemandAmount) {

        return new ApprovalCommonResponse(
            "0000".equals(responseCode),
            version,
            mid,
            serviceCode,
            command,
            orderId,
            orderDate,
            transactionId,
            responseCode,
            responseMessage,
            detailResponseCode,
            detailResponseMessage,
            authDate,
            authAmount,
            eventUse,
            eventDiscountAmount,
            eventDemandAmount
        );
    }
}
