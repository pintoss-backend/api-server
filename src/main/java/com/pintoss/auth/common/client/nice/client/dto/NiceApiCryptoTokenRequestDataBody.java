package com.pintoss.auth.common.client.nice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NiceApiCryptoTokenRequestDataBody {
    public String req_dtim;
    public String req_no;
    public String enc_mode;
}
