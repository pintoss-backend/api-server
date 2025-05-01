package com.pintoss.auth.common.external.nice.client.dto;

import lombok.Data;

@Data
public class NiceApiAccessTokenResponseDataBody {
    private String access_token;
    private String expires_in;
    private String token_type;
    private String scope;
}
