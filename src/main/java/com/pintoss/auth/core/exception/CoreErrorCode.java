package com.pintoss.auth.core.exception;

public enum CoreErrorCode {

    // Cart Item Errors
    CART_ITEM_NOT_FOUND("CART_ITEM_NOT_FOUND", "장바구니 아이템을 찾을 수 없습니다.", HttpErrorType.NOT_FOUND),
    CART_ITEM_ALREADY_DELETED("CART_ITEM_ALREADY_DELETED", "이미 삭제된 장바구니 아이템입니다.", HttpErrorType.BAD_REQUEST),
    INVALID_CART_ITEM_QUANTITY("4009", "장바구니 아이템의 수량이 유효하지 않습니다.", HttpErrorType.BAD_REQUEST),

    // Order Errors
    ORDER_ALREADY_CANCELED("40013", "이미 취소된 주문입니다.", HttpErrorType.BAD_REQUEST),
    ORDER_ALREADY_PAID("40014", "이미 결제된 주문입니다.", HttpErrorType.BAD_REQUEST),
    ORDER_ALREADY_REFUNDED("40015", "이미 환불된 주문입니다.", HttpErrorType.BAD_REQUEST),
    ORDER_ALREADY_ISSUED("40016", "이미 발급된 주문입니다.", HttpErrorType.BAD_REQUEST),
    ORDER_NOT_REFUNDABLE("40020", "환불이 불가능한 주문입니다.", HttpErrorType.BAD_REQUEST),
    ORDER_ALREADY_PAYMENT_FAILED("40021", "이미 결제 실패된 주문입니다.", HttpErrorType.BAD_REQUEST),

    // Order Item Errors
    ORDER_ITEM_ALREADY_ISSUED("40017", "이미 발급된 주문 아이템입니다.", HttpErrorType.BAD_REQUEST),
    ORDER_ITEM_NOT_FOUND("40018", "주문 항목을 찾을 수 없습니다.", HttpErrorType.NOT_FOUND),

    // Payment Errors
    PAYMENT_APPROVAL_FAILED("40011", "결제 승인이 거절되었습니다.", HttpErrorType.BAD_REQUEST),
    PAYMENT_APPROVED_AMOUNT_MISMATCH("40012", "결제 승인 금액이 주문 금액과 일치하지 않습니다.", HttpErrorType.BAD_REQUEST),

    // Voucher Errors
    VOUCHER_ISSUER_ALREADY_EXISTS("40013", "이미 존재하는 상품권 발급자입니다.", HttpErrorType.BAD_REQUEST),

    ;
    private final String code;
    private final String message;
    private final HttpErrorType httpErrorType;

    CoreErrorCode(String code, String message, HttpErrorType httpErrorType) {
        this.code = code;
        this.message = message;
        this.httpErrorType = httpErrorType;
    }
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpErrorType getHttpErrorType() {
        return httpErrorType;
    }
}
