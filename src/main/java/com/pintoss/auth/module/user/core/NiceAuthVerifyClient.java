package com.pintoss.auth.module.user.core;

import com.pintoss.auth.module.user.application.dto.NiceVerificationResult;

public interface NiceAuthVerifyClient {

    NiceVerificationResult verify(String tokenVersionId, String encData, String integrityValue);
}
