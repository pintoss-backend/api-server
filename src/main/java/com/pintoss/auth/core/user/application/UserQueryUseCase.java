package com.pintoss.auth.core.user.application;

import com.pintoss.auth.api.security.SecurityContextUtils;
import com.pintoss.auth.core.user.domain.UserInfo;
import com.pintoss.auth.core.user.core.UserViewer;
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
