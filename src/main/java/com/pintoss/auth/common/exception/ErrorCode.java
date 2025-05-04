package com.pintoss.auth.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 Bad Request
    // 📄 요청 관련
    BAD_REQUEST("400", "잘못된 요청입니다."),
    REQUEST_FIELD_MISSING("4001", "필수 입력 항목이 누락되었습니다."),
    REQUEST_FIELD_INVALID("4002", "입력한 값이 올바르지 않습니다."),
    REQUEST_TYPE_MISMATCH("4003", "요청 값의 형식이 일치하지 않습니다."),
    REQUEST_VALIDATION_FAILED("4005", "유효성 검사에 실패하였습니다."),
    FILE_TYPE_NOT_SUPPORTED("4006", "지원하지 않는 파일 형식입니다."),
    UNSUPPORTED_OAUTH2_PROVIDER("4007", "지원하지 않는 OAuth2 제공자입니다."),
    INVALID_REFRESH_TOKEN_SUBJECT("4008", "요청한 리프레쉬 토큰의 사용자 정보가 올바르지 않습니다."),
    INVALID_CART_ITEM_QUANTITY("4009", "장바구니 아이템의 수량이 1개 이상이어야합니다."),

    // 401 Unauthorized
    // AUTH 관련
    UNAUTHORIZED("401", "잘못된 자격증명 입니다."),
    AUTH_TOKEN_EXPIRED("4011", "토큰이 만료되었습니다."),
    AUTH_INVALID_ACCESS_TOKEN("4012","유효하지 않은 액세스 토큰입니다."),
    AUTH_MISSING_ACCESS_TOKEN("4013", "액세스 토큰이 제공되지 않았습니다."),
    AUTH_INVALID_REFRESH_TOKEN("4014","유효하지 않은 리프레쉬 토큰입니다."),
    AUTH_MISSING_REFRESH_TOKEN("4015", "리프레쉬 토큰이 제공되지 않았습니다."),
    AUTH_CREDENTIALS_INVALID("4016", "아이디 또는 비밀번호가 올바르지 않습니다."),
    AUTH_PASSWROD_INVALID("4017", "비밀번호가 올바르지 않습니다."),

    // 403 Forbidden
    AUTH_ACCESS_DENIED("40301", "해당 요청에 대한 권한이 없습니다."),

    // 404 Not Found
    RESOURCE_NOT_FOUND("4041", "해당 리소스를 찾을 수 없습니다."),
    USER_NOT_FOUND("4042", "해당 사용자를 찾을 수 없습니다."),

    // 405 Method Not Allowed
    METHOD_NOT_ALLOWED("4051", "허용되지 않은 메서드입니다."),

    // 409 Conflict
    DUPLICATE_RESOURCE("4091", "이미 등록된 리소스입니다."),
    CONFLICT_REQUEST("4092", "요청이 충돌하였습니다."),
    DUPLICATE_USER("4093", "이미 등록된 사용자입니다."),

    // 415 Unsupported Media Type
    UNSUPPORTED_MEDIA_TYPE("4151", "지원하지 않는 콘텐츠 타입입니다."),

    // 429 Too Many Requests
    TOO_MANY_REQUESTS("4291", "요청이 너무 많습니다. 나중에 다시 시도해주세요."),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR("5001", "서버 내부 오류가 발생했습니다."),
    IMAGE_UPLOAD_FAILED("5005", "이미지 업로드에 실패했습니다."),
    FILE_UPLOAD_FAILED("50001", "파일 업로드에 실패하였습니다."),
    JSON_SERIALIZATION_ERROR("50011", "요청 데이터를 JSON 형식으로 변환할 수 없습니다."),



    UNSUPPORTED_ENCRYPTION_ALGORITHM("5006", "지원하지 않는 암호화 알고리즘입니다."),
    INVALID_HMAC_KEY("5007", "잘못된 HMAC 키입니다."),
    UNSUPPORTED_PADDING_SCHEMA("5008", "지원하지 않는 패딩스킴(PKCS5Padding)입니다."),
    INVALID_ENCRYPTION_KEY("5009", "암호화 키가 잘못되었거나 길이가 적합하지 않습니다."),
    INVALID_ALGORITHM_PARAMETER("50010", "초기화 벡터(IV)가 잘못되었거나 형식이 맞지 않습니다."),
    JSON_SERIALIZATION("50011", "요청 데이터를 JSON 형식으로 변환할 수 없습니다."),
    ILLEGAL_BLOCK_SIZE("50012", "암호화 시 블록 크기가 부적합합니다."),
    BAD_PADDING("50013", "패딩이 잘못되었거나 데이터 손상이 의심됩니다.");

    private final String code;
    private final String message;
}
