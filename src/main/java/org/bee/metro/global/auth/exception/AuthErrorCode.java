package org.bee.metro.global.auth.exception;

import org.bee.metro.global.exception.ErrorCode;

public enum AuthErrorCode implements ErrorCode {
    EXPIRED_TOKEN("A001", "토큰이 만료되었습니다."),
    INVALID_TOKEN("A002", "유효하지 않은 토큰입니다."),
    UNAUTHORIZED("A003", "권한이 없습니다."),
    NOT_FOUND_TOKEN("A004", "토큰이 없습니다.")
    ;

    private final String code;
    private final String message;

    AuthErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
