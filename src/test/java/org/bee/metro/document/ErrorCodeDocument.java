package org.bee.metro.document;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.bee.metro.context.DocumentTest;
import org.bee.metro.document.errorcode.ErrorCodeFieldsSnippet;
import org.junit.jupiter.api.Test;

public class ErrorCodeDocument extends DocumentTest {

    @Test
    void 에러_코드_생성() throws Exception {
        ErrorCodeFieldsSnippet errorCodeFieldsSnippet = new ErrorCodeFieldsSnippet("error-code", "error-code-template");

        mockMvc.perform(get("/test/error-code")
                        .header("Authorization", accessToken))
                .andDo(print())
                .andDo(document("error-code", errorCodeFieldsSnippet));
    }
}
