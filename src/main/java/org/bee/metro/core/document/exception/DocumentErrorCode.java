package org.bee.metro.core.document.exception;

import org.bee.metro.global.exception.ErrorCode;

public enum DocumentErrorCode implements ErrorCode {
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
