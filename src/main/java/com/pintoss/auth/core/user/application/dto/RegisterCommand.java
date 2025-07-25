package com.pintoss.auth.core.user.application.dto;

import com.pintoss.auth.storage.user.jpa.entity.LoginType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class RegisterCommand {

    private String email;
    private String password;
    private String name;
    private String phone;
    private LoginType loginType;

    public static RegisterCommand create(String email, String password, String name, String phone, LoginType loginType) {

        return new RegisterCommand(email, password, name, phone, loginType);
    }
}
