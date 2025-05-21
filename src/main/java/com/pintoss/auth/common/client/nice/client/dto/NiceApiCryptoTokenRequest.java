package com.pintoss.auth.common.client.nice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NiceApiCryptoTokenRequest {
    public NiceApiCryptoTokenRequestDataHeader dataHeader;
    public NiceApiCryptoTokenRequestDataBody dataBody;
}
