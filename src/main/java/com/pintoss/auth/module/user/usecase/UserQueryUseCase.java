package com.pintoss.auth.module.user.usecase;

import com.pintoss.auth.common.security.SecurityContextUtils;
import com.pintoss.auth.module.user.model.UserInfo;
import com.pintoss.auth.module.user.usecase.service.UserViewer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryUseCase {

    private final UserViewer userViewer;

    public UserInfo getUserInfo() {
        Long userId = SecurityContextUtils.getUserId();
        return userViewer.getUserInfo(userId);

    }

}
