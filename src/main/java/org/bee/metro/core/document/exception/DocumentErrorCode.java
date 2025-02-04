package org.bee.metro.core.document.exception;

import org.bee.metro.global.exception.ErrorCode;

public enum DocumentErrorCode implements ErrorCode {
    ARGUMENT_IS_NULL("D001", "해당 인자는 null일 수 없습니다")
    ;

    private final String code;
    private final String message;

    DocumentErrorCode(String code, String message) {
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
