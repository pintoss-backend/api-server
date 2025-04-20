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

    // 415 Unsupported Media Type
    UNSUPPORTED_MEDIA_TYPE("4151", "지원하지 않는 콘텐츠 타입입니다."),

    // 429 Too Many Requests
    TOO_MANY_REQUESTS("4291", "요청이 너무 많습니다. 나중에 다시 시도해주세요."),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR("5001", "서버 내부 오류가 발생했습니다."),
    IMAGE_UPLOAD_FAILED("5005", "이미지 업로드에 실패했습니다."),
    FILE_UPLOAD_FAILED("50001", "파일 업로드에 실패하였습니다."),
    JSON_SERIALIZATION_ERROR("50011", "요청 데이터를 JSON 형식으로 변환할 수 없습니다.");

    private final String code;
    private final String message;
}
