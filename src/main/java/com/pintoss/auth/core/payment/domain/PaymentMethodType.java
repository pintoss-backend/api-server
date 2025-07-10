package com.pintoss.auth.core.payment.domain;

import com.pintoss.auth.common.exception.client.BadRequestException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethodType {

    CARD("0900", "card"),
    PHONE("1100", "phone");

    private final String serviceCode;
    private final String description;

    public static PaymentMethodType fromDescription(String description) {
        for (PaymentMethodType type : values()) {
            if (type.getDescription().equals(description)) {
                return type;
            }
        }
        throw new BadRequestException("잘못된 결제 설명: " + description);
    }

    public static PaymentMethodType fromServiceCode(String serviceCode) {
        for (PaymentMethodType type : values()) {
            if (type.getServiceCode().equals(serviceCode)) {
                return type;
            }
        }
        throw new BadRequestException("잘못된 결제 서비스 코드: " + serviceCode);
    }
}

