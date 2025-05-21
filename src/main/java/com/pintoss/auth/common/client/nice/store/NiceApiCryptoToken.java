package com.pintoss.auth.common.client.nice.store;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NiceApiCryptoToken {
    private String reqDtim;
    private String reqNo;
    private String siteCode;
    private String tokenVersionId;
    private String tokenValue;
    private LocalDateTime expiredAt;

    public boolean isExpired(){
        LocalDateTime now = LocalDateTime.now();
        if ( expiredAt.isBefore(now) ){
            return true;
        }
        return false;
    }
}
