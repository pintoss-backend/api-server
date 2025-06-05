package com.pintoss.auth.module.user.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LoginCommand {

    private String email;
    private String password;

    public static LoginCommand create(String email, String password) {
        return new LoginCommand(email, password);
    }
}
