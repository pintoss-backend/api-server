package com.pintoss.auth.core.user.application.flow.external;

import com.pintoss.auth.api.auth.dto.IdentityVerifyPurpose;
import com.pintoss.auth.core.user.application.dto.NiceEncryptedDataResult;

public interface NiceAuthRequestClient {
    NiceEncryptedDataResult requestAuthData(IdentityVerifyPurpose purpose);
}
