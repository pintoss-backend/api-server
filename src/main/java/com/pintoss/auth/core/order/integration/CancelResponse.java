package com.pintoss.auth.core.order.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelResponse {

    private String responseCode;
    private String approvalCode;
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
