package com.pintoss.auth.common.external.nice.store;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NiceApiAccessToken {

    private String value;

    private LocalDateTime expiredAt;

    public boolean isExpired(){
        LocalDateTime now = LocalDateTime.now();
        if ( expiredAt.isBefore(now) ){
            return true;
        }
        return false;
    }
}
