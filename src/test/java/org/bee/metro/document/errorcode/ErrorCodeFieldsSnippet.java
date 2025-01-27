package org.bee.metro.document.errorcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bee.metro.core.auth.exception.AuthErrorCode;
import org.bee.metro.core.member.exception.MemberErrorCode;
import org.bee.metro.global.exception.ErrorCode;
import org.bee.metro.global.exception.GlobalErrorCode;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.snippet.TemplatedSnippet;

public class ErrorCodeFieldsSnippet extends TemplatedSnippet {

    private List<Class<? extends ErrorCode>> errorCodes = List.of(
            AuthErrorCode.class,
            MemberErrorCode.class,
            GlobalErrorCode.class
    );

    public ErrorCodeFieldsSnippet(String snippetName, String templateName) {
        super(snippetName, templateName, null);
    }

    @Override
    protected Map<String, Object> createModel(Operation operation) {
        Map<String, Object> fields = new HashMap<>();
        List<Map<String, String>> errorCodeValues = createErrorCodeValues();
        fields.put("fields", errorCodeValues);
        return fields;
    }

    private List<Map<String, String>> createErrorCodeValues() {
        return errorCodes.stream()
                .flatMap(errorCodeClass -> Arrays.stream(errorCodeClass.getEnumConstants()))
                .map(errorCode -> {
                    Map<String, String> errorCodeValue = new HashMap<>();
                    errorCodeValue.put("code", errorCode.getCode());
                    errorCodeValue.put("message", errorCode.getMessage());
                    return errorCodeValue;
                })
                .toList();
    }
}
