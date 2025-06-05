package com.pintoss.auth.module.user.application;

import com.pintoss.auth.api.auth.dto.IdentityVerifyPurpose;
import com.pintoss.auth.module.user.application.dto.NiceEncryptedDataResult;
import com.pintoss.auth.module.user.application.dto.NiceVerificationResult;
import com.pintoss.auth.module.user.core.NiceAuthRequestClient;
import com.pintoss.auth.module.user.core.NiceAuthVerifyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdentityVerificationUseCase {

    private final NiceAuthRequestClient niceAuthRequestClient;
    private final NiceAuthVerifyClient niceVerifyClient;

    public NiceEncryptedDataResult getEncryptedData(IdentityVerifyPurpose purpose) {
        return niceAuthRequestClient.requestAuthData(purpose);
    }

    public NiceVerificationResult verify(String tokenVersionId, String encData, String integrityValue) {
        return niceVerifyClient.verify(tokenVersionId, encData, integrityValue);
    }
}
