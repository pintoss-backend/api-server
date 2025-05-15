package com.pintoss.auth.module.user.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendCodeRequest {

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    private final String email;

    @NotBlank(message = "이름은 필수 항목입니다.")
    private final String name;

    @NotBlank(message = "전화번호는 필수 항목입니다.")
    private final String phone;

}
