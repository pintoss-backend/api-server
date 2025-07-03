package com.pintoss.auth.core.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserInfo {
    private String email;
    private String name;
    private String phone;
}
