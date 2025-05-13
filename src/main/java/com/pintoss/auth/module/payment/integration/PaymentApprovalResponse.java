package com.pintoss.auth.module.payment.integration;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
* 결제 요청 응답 전문을 파싱해서 저장하는 객체
* */
@Data
public class PaymentApprovalResponse {

    private String version;
    private String mid;
    private String serviceCode;
    private String command;
    private String orderId;
    private String orderDate;
    private String numberOfRecord;

    private String transactionId; // 1001
    private String responseCode;  // 1002
    private String responseMessage; // 1003
    private String detailResponseCode; // 1009
    private String detailResponseMessage; // 1010

    private String authNumber; // 1004
    private String authDate;   // 1005
    private String authAmount; // 1007
    private String quota;      // 0031
    private String cardCompanyCode; // 0034
    private String pinNumber; // 0008
    private String partCancelUse; // 5312
    private String taxAmount; // 5304
    private String taxFreeAmount; // 5305
    private String simpleAffName; // 5315
    private String simpleDealType; // 5321
    private String cashReceiptAuthNumber; // 5337
    private String eventUse; // 5029
    private String eventDiscountAmount; // 5030
    private String eventDemandAmount;   // 5031

    private Map<String, String> etcFields = new HashMap<>();

    public void putField(String tag, String value) {
        switch (tag) {
            case "1001": transactionId = value; break;
            case "1002": responseCode = value; break;
            case "1003": responseMessage = value; break;
            case "1004": authNumber = value; break;
            case "1005": authDate = value; break;
            case "1007": authAmount = value; break;
            case "1009": detailResponseCode = value; break;
            case "1010": detailResponseMessage = value; break;
            case "0031": quota = value; break;
            case "0034": cardCompanyCode = value; break;
            case "0008": pinNumber = value; break;
            case "5312": partCancelUse = value; break;
            case "5304": taxAmount = value; break;
            case "5305": taxFreeAmount = value; break;
            case "5315": simpleAffName = value; break;
            case "5321": simpleDealType = value; break;
            case "5337": cashReceiptAuthNumber = value; break;
            case "5029": eventUse = value; break;
            case "5030": eventDiscountAmount = value; break;
            case "5031": eventDemandAmount = value; break;
            default: etcFields.put(tag, value);
        }
    }

    public boolean isSuccess() {
        return "0000".equals(responseCode);
    }
}
