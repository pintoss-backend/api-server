package com.pintoss.auth.module.order.application.model;

public enum OrderItemStatus {

    PENDING("발급 대기"),
    PROCESSING("발급 진행 중"),
    ISSUED("발급 완료");
    private final String description;

    // 생성자
    OrderItemStatus(String description) {
        this.description = description;
    }

    // 설명을 반환하는 메서드
    public String getDescription() {
        return description;
    }
}
