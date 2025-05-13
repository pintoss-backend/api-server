package com.pintoss.auth.module.order.integration;

import com.pintoss.auth.module.payment.application.PurchaseCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@Builder
public class PurchaseResponse {
    private String responseCode;
    private String authNo;
    private String openFlag;
    private String cardNo;
    private String remainPrice;
    private String itemName;
    private String printFlag1;
    private String printMsg1;
    private String printFlag2;
    private String printMsg2;
    private String printFlag3;
    private String printMsg3;
    private String printFlag4;
    private String printMsg4;
    private String printFlag5;
    private String printMsg5;

}

