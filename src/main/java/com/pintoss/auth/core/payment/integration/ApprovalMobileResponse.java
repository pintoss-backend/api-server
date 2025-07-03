package com.pintoss.auth.core.payment.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApprovalMobileResponse {

    private String partCancelType; // 7049
    private String mobileNumber;   // 0007
}
