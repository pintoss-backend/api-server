package com.pintoss.auth.core.user.application;

import com.pintoss.auth.api.support.security.SecurityContextUtils;
import com.pintoss.auth.storage.user.jpa.entity.UserInfo;
import com.pintoss.auth.core.user.application.flow.viewer.UserViewer;
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
