package com.pintoss.auth.core.user.application.flow.external;

import com.pintoss.auth.core.user.application.dto.NiceVerificationResult;

public interface NiceAuthVerifyClient {

    NiceVerificationResult verify(String tokenVersionId, String encData, String integrityValue);
}
