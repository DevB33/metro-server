package org.bee.metro.core.block.exception;

import org.bee.metro.global.exception.ErrorCode;

public class BlockErrorCode implements ErrorCode {

    private final String code;
    private final String message;

    public BlockErrorCode(String code, String message) {
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
