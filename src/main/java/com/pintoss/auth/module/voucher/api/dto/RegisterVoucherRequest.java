package com.pintoss.auth.module.voucher.api.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterVoucherRequest {

    @NotNull(message = "상품권 명은 필수 항목입니다.")
    private String name;

    @NotNull(message = "상품권 가격은 필수 항목입니다.")
    @Min(value = 0, message = "상품권 가격은 0 이상이어야 합니다.")
    private Long price;
}
