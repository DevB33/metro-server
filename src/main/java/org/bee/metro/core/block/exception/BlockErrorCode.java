package org.bee.metro.core.block.exception;

import org.bee.metro.global.exception.ErrorCode;

public enum BlockErrorCode implements ErrorCode {
    INVALID_ORDER("B001", "블록의 순서는 0보다 작을 수 없습니다."),
    INVALID_DOCUMENT_ID("B002", "블록의 문서 ID는 필수입니다."),
    INVALID_BLOCK_ID("B003", "블록의 ID는 필수입니다."),
    INVALID_CONVERT_STYLE("B004", "스타일 정보를 변환하는데 실패했습니다."),
    INVALID_MEMBER_ID("B005", "멤버 ID는 필수입니다."),
    NOT_FOUND_NODE("B006", "노드가 존재하지 않습니다."),
    UNAUTHORIZED("B007", "해당 권한이 없습니다."),
    CONVERT_TO_STRING_FAILED("B008", "해당 노드을 문자열로 변경할 수 없습니다."),
    CONVERT_TO_NODES_FAILED("B009", "해당 문자열을 노드로 변경할 수 없습니다."),
    NOT_FOUND_BLOCK("B010", "블록을 찾을 수 없습니다."),;

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
