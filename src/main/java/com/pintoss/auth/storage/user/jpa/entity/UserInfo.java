package com.pintoss.auth.storage.user.jpa.entity;

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
