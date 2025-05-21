package com.pintoss.auth.common.client.nice.client.dto;

import lombok.Data;

@Data
public class NiceApiCryptoTokenResponse {
    public NiceApiCryptoTokenResponseDataHeader dataHeader;
    public NiceApiCryptoTokenResponseDataBody dataBody;
}
