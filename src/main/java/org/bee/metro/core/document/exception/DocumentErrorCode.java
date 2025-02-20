package org.bee.metro.core.document.exception;

import org.bee.metro.global.exception.ErrorCode;

public enum DocumentErrorCode implements ErrorCode {
    ARGUMENT_IS_NULL("D001", "해당 인자는 null일 수 없습니다"),
    NOT_FOUND("D002", "해당 문서를 찾을 수 없습니다"),
    UNAUTHORIZED("D003", "해당 문서에 대한 권한이 없습니다"),
    INVALID_DOCUMENT_FIELD_TYPE("D004", "해당 문서 필드 타입이 올바르지 않습니다"),
    CONVERT_TO_STRING_FAILED("D005", "해당 태그 목록을 문자열로 변경할 수 없습니다"),
    CONVERT_TO_TAGS_FAILED("D006", "해당 문자열을 태그 목록으로 변경할 수 없습니다")
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
