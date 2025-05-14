package com.pintoss.auth.module.user.usecase;

import com.pintoss.auth.module.user.api.IdentityVerifyPurpose;
import com.pintoss.auth.module.user.usecase.dto.NiceEncryptedDataResult;
import com.pintoss.auth.module.user.usecase.dto.NiceVerificationResult;
import com.pintoss.auth.module.user.usecase.service.NiceAuthRequestClient;
import com.pintoss.auth.module.user.usecase.service.NiceAuthVerifyClient;
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
