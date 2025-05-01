package com.pintoss.auth.common.external.nice.client.dto;

import lombok.Data;

@Data
public class NiceApiCryptoTokenResponseDataBody {
    private String rsp_cd;
    private String res_msg;
    private String result_cd;
    private String site_code;
    private String token_version_id;
    private String token_val;
    private long period;
}
