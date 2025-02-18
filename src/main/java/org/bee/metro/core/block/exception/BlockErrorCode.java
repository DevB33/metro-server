package org.bee.metro.core.block.exception;

import org.bee.metro.global.exception.ErrorCode;

public enum BlockErrorCode implements ErrorCode {
    INVALID_ORDER("B001", "블록의 순서는 0보다 작을 수 없습니다."),
    INVALID_DOCUMENT_ID("B002", "블록의 문서 ID는 필수입니다.");

    private final String code;
    private final String message;

    BlockErrorCode(String code, String message) {
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
