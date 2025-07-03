package com.pintoss.auth.api.auth.dto;

import com.pintoss.auth.core.user.domain.LoginType;
import com.pintoss.auth.core.user.application.dto.RegisterCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest  {

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수 항목입니다.")
    private String phone;

    @NotNull(message = "로그인 타입을 필수 항목입니다.")
    private LoginType loginType;

    public RegisterCommand to() {
        return RegisterCommand.create(email, password, name, phone, loginType);
    }
}

