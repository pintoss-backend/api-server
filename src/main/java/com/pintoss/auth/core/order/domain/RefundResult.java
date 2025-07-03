package com.pintoss.auth.core.order.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefundResult {
    private final RefundStatus status;
    private final String message;

    public static RefundResult success() {
        return new RefundResult(RefundStatus.SUCCESS, null);
    }

    public static RefundResult failure(String message) {
        return new RefundResult(RefundStatus.FAILURE, message);
    }

    public boolean isSuccess() {
        return status == RefundStatus.SUCCESS;
    }
}
