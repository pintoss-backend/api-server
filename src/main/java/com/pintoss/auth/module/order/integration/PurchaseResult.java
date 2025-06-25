package com.pintoss.auth.module.order.integration;

import lombok.Getter;

@Getter
public class PurchaseResult {

    private boolean isSuccess;
    private String approvalCode;
    private String pinNo;
    private String itemName;
    private Long price;
    private String msg;

    public PurchaseResult(boolean isSuccess, String approvalCode, String pinNo, String itemName,
        Long price, String msg) {
        this.isSuccess = isSuccess;
        this.approvalCode = approvalCode;
        this.pinNo = pinNo;
        this.itemName = itemName;
        this.price = price;
        this.msg = msg;
    }
}
