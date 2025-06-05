package com.pintoss.auth.module.user.core;

import com.pintoss.auth.api.auth.dto.IdentityVerifyPurpose;
import com.pintoss.auth.module.user.application.dto.NiceEncryptedDataResult;

public interface NiceAuthRequestClient {
    NiceEncryptedDataResult requestAuthData(IdentityVerifyPurpose purpose);
}
