package com.pintoss.auth.common.client.nice.encryption.dto;

import lombok.Data;

@Data
public class NiceAuthStandardAuthRequest {

    private String requestno;
    private String returnurl;
    private String sitecode;
    private String authtype;
    private String mobilceco;
    private String businessno;
    private String methodtype;
    private String popupyn;
    private String receivedata;

    public NiceAuthStandardAuthRequest(String requestno, String returnurl, String sitecode, String purpose) {
        this.requestno = requestno;
        this.returnurl = returnurl;
        this.sitecode = sitecode;
        this.methodtype = "get";
        this.popupyn = "Y";
        this.receivedata = purpose;
    }
}
