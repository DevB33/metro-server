package org.bee.metro.document;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.responseCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.bee.metro.context.DocumentTest;
import org.bee.metro.core.auth.dto.MemberLoginRequest;
import org.bee.metro.core.auth.dto.MemberToken;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser
@AutoConfigureMockMvc(addFilters = false)
public class AuthDocument extends DocumentTest {

    @Nested
    class 로그인 {

        @Test
        void 로그인이_성공하면_200을_반환한다() throws Exception {
            MemberLoginRequest memberLoginRequest = new MemberLoginRequest(
                    "authorizationCode", "state", "test");
            MemberToken memberToken = new MemberToken("accessToken", "refreshToken");
            given(authService.login(any(), any(), any())).willReturn(memberToken);

            mockMvc.perform(
                        post("/auth/login")
                                .content(objectMapper.writeValueAsString(memberLoginRequest))
                                .contentType("application/json")
                    )
                    .andExpect(status().isOk())
                    .andDo(
                            document("auth/login",
                                    preprocessRequest(prettyPrint()),
                                    requestFields(
                                            fieldWithPath("authorizationCode").description("OAuth 인증 코드"),
                                            fieldWithPath("state").description("OAuth 상태값"),
                                            fieldWithPath("provider").description("OAuth 제공자 (예: GOOGLE, NAVER, KAKAO)")
                                    ),
                                    responseCookies(
                                            cookieWithName("accessToken").description("액세스 토큰"),
                                            cookieWithName("refreshToken").description("리프레시 토큰")
                                    )
                            )
                    );
        }
    }

    @Nested
    class 로그아웃 {

        @Test
        void 로그아웃이_성공하면_200을_반환한다() throws Exception {
            mockMvc.perform(post("/auth/logout"))
                    .andExpect(status().isOk())
                    .andDo(document("auth/logout",
                            preprocessRequest(prettyPrint()),
                            responseCookies(
                                    cookieWithName("accessToken").description("액세스 토큰 (삭제됨)"),
                                    cookieWithName("refreshToken").description("리프레시 토큰 (삭제됨)")
                            )
                    ));
        }
    }
}
