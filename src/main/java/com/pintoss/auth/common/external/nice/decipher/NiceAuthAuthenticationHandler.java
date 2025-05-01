package com.pintoss.auth.common.external.nice.decipher;

import com.pintoss.auth.common.external.nice.decipher.dto.AuthenticationDataDecipherResult;
import org.springframework.stereotype.Component;

@Component
public class NiceAuthAuthenticationHandler {
    public boolean handler(AuthenticationDataDecipherResult result) {
        if( result == null ) {
            return false;
        }
        if( !result.getMobileno().equals("0000")) {
            return false;
        }
        return true;
    }
}
