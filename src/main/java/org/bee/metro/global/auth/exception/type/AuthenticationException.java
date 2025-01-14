package org.bee.metro.global.auth.exception.type;

import org.bee.metro.global.exception.ErrorCode;
import org.bee.metro.global.exception.type.CustomException;

public class AuthenticationException extends CustomException {
    public AuthenticationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
