package com.pintoss.auth.module.user.usecase.service;

import com.pintoss.auth.module.user.usecase.dto.NiceVerificationResult;

public interface NiceAuthVerifyClient {

    NiceVerificationResult verify(String tokenVersionId, String encData, String integrityValue);
}
