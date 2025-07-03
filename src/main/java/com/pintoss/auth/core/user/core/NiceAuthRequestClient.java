package com.pintoss.auth.core.user.core;

import com.pintoss.auth.api.auth.dto.IdentityVerifyPurpose;
import com.pintoss.auth.core.user.application.dto.NiceEncryptedDataResult;

public interface NiceAuthRequestClient {
    NiceEncryptedDataResult requestAuthData(IdentityVerifyPurpose purpose);
}
