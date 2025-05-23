package com.pintoss.auth.module.user.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateRequest {

    @NotBlank(message = "현재 비밀번호는 필수 항목입니다.")
    private String originPassword;

    @NotBlank(message = "새 비밀번호는 필수 항목입니다.")
    private String newPassword;

}
