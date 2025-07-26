package com.pintoss.auth.core.payment.domain;

public enum PaymentStatus {
    PENDING("결제 대기"),       // 결제 대기 (결제 시도 전)
    SUCCESS("결제 성공"),      // 결제 성공
    FAILED("결제 실패"),       // 결제 실패
    CANCELED("결제 취소");      // 결제 취소 (환불 포함)

    private final String description;

    // 생성자
    PaymentStatus(String description) {
        this.description = description;
    }

    // 설명을 반환하는 메서드
    public String getDescription() {
        return description;
    }

}
