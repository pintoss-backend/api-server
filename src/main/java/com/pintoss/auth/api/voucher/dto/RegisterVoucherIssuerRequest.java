package com.pintoss.auth.api.voucher.dto;

import com.pintoss.auth.core.voucher.usecase.dto.RegisterVoucherIssuerCommand;
import com.pintoss.auth.core.voucher.usecase.dto.RegisterVoucherIssuerCommand.RegisterVoucherCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterVoucherIssuerRequest {

    @NotBlank(message = "상품 이름은 필수 항목입니다.")
    private String name;

    @NotBlank(message = "상품 코드 번호는 필수 항목입니다.")
    private String code;

    @NotNull(message = "카드 할인 금액은 필수 항목입니다.")
    @Min(value = 0, message = "카드 할인 금액은 0 이상이어야 합니다.")
    private BigDecimal cardDiscount = BigDecimal.ZERO;

    @NotNull(message = "전화 할인 금액은 필수 항목입니다.")
    @Min(value = 0, message = "전화 할인 금액은 0 이상이어야 합니다.")
    private BigDecimal phoneDiscount = BigDecimal.ZERO;

    @NotBlank(message = "홈페이지 주소는 필수 항목입니다.")
    private String homePage;

    @NotBlank(message = "고객 센터 정보는 필수 항목입니다.")
    private String csCenter;

    @NotBlank(message = "설명은 필수 항목입니다.")
    private String description;

    @NotBlank(message = "발행자는 필수 항목입니다.")
    private String publisher;

    @NotBlank(message = "로고 이미지는 필수 항목입니다.")
    private String imageUrl;

    @NotBlank(message = "상품 유의사항은 필수 항목입니다.")
    private String note;

    @NotEmpty(message = "상품권 종류는 1개 이상이어야 합니다.")
    private List<RegisterVoucherRequest> vouchers = new ArrayList<>();

    @Min(value = 0, message = "수수료는 0 이상이어야 합니다.")
    private BigDecimal fee = BigDecimal.ZERO;

    @Data
    @NoArgsConstructor
    public static class RegisterVoucherRequest {

        @NotNull(message = "상품권 명은 필수 항목입니다.")
        private String name;

        @NotNull(message = "상품권 가격은 필수 항목입니다.")
        @Min(value = 0, message = "상품권 가격은 0 이상이어야 합니다.")
        private Long price;

        public RegisterVoucherCommand toCommand() {
            return RegisterVoucherCommand.builder()
                .name(name)
                .price(price)
                .build();
        }
    }

    public RegisterVoucherIssuerCommand to() {
        return RegisterVoucherIssuerCommand.builder()
            .name(this.name)
            .code(this.code)
            .cardDiscount(this.cardDiscount)
            .phoneDiscount(this.phoneDiscount)
            .homePage(this.homePage)
            .csCenter(this.csCenter)
            .description(this.description)
            .publisher(this.publisher)
            .imageUrl(this.imageUrl)
            .note(this.note)
            .vouchers(this.vouchers.stream()
                .map(RegisterVoucherRequest::toCommand)
                .toList()
            )
            .fee(this.fee)
            .build();
    }
}
