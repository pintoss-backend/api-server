package com.pintoss.auth.client.nice.client.dto;

import lombok.Data;

@Data
public class NiceApiAccessTokenResponse {
    private NiceApiAccessTokenResponseDataHeader dataHeader;
    private NiceApiAccessTokenResponseDataBody dataBody;
}
