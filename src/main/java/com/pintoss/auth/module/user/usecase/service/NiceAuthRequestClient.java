package com.pintoss.auth.module.user.usecase.service;

import com.pintoss.auth.module.user.api.IdentityVerifyPurpose;
import com.pintoss.auth.module.user.usecase.dto.NiceEncryptedDataResult;

public interface NiceAuthRequestClient {
    NiceEncryptedDataResult requestAuthData(IdentityVerifyPurpose purpose);
}
