package com.pintoss.auth.common.external.nice.client.dto;

import lombok.Data;

@Data
public class NiceApiCryptoTokenResponse {
    public NiceApiCryptoTokenResponseDataHeader dataHeader;
    public NiceApiCryptoTokenResponseDataBody dataBody;
}
