package com.pintoss.auth.module.payment.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* of : 가장 일반적인 팩토리 메섣, 주로 여러 파라미터를 받는 경우
* from : 다른 객체로부터 값을 추출해서 사용하는 경우
* valueOf : 값 객체를 만들거나 문자열 등 단일 값으로 생성하는 경우
* create : 뭔가 새로운 객체를 만들어낸다는 의미를 담고 싶을 때
* parse : 문자열 등 파싱이 필요한 경우
* */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private String orderId;

    public static PaymentResponse of(String orderId) {
        return new PaymentResponse(orderId);
    }
}
