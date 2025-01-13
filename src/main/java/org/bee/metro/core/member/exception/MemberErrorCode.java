package org.bee.metro.core.member.exception;

import org.bee.metro.global.exception.ErrorCode;

public enum MemberErrorCode implements ErrorCode {
    ARGUMENT_IS_NULL("M001", "인자는 null일 수 없습니다."),
    INVALID_EMAIL_FORMAT("M002", "이메일 형식이 올바르지 않습니다.")
    ;

    private final String code;
    private final String message;

    MemberErrorCode(String code, String message) {
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
