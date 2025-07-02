package com.pintoss.auth.module.order.domain;

public enum OrderStatus {
    PENDING("주문 접수"),      // 주문 접수 (결제 전)
    PROCESSING("결제 진행"),   // 결제 진행 중
    PAID("결제 완료"),    // 결제 완료 (주문 확정)
    PAYMENT_FAILED("결제 실패"), // 결제 실패
    CANCELED("주문 취소"),     // 주문 취소 (결제 전 취소)
    REFUNDED("환불 성공"),   // 결제 완료 후 취소 (환불 성공)
    REFUND_FAILED("환불 실패"),     // 환불 실패
    PARTIAL_REFUNDED("부분 환불"),      // 일부 환불
    ISSUED("상품권 발급 완료"),
    ISSUE_PROCESSING("상품권 발급 진행 중"),
    ISSUE_FAILED("상품권 발급 실패"),
    ISSUED_PARTIAL("상품권 일부 발급 완료");

    private final String description;

    // 생성자
    OrderStatus(String description) {
        this.description = description;
    }

    // 설명을 반환하는 메서드
    public String getDescription() {
        return description;
    }
}

//public enum PaymentStatus {
//    PENDING,      // 결제 대기 (결제 시도 전)
//    IN_PROGRESS,  // 결제 진행 중
//    SUCCESS,      // 결제 성공
//    FAILED,       // 결제 실패
//    CANCELED      // 결제 취소 (환불 포함)
//}
