package org.bee.metro.document;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.bee.metro.context.DocumentTest;
import org.bee.metro.core.member.domain.Member;
import org.bee.metro.core.member.dto.MemberPayload;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@AutoConfigureMockMvc
public class MemberDocument extends DocumentTest {

    @Nested
    class 멤버_조회 {

        @Test
        void 멤버_조회가_성공하면_200을_반환한다() throws Exception {
            Member member = new Member(UUID.randomUUID(), "name", "email@email.com", "avatar_url", "oauthId");
            given(memberService.findMemberById(any())).willReturn(member);

            mockMvc.perform(get("/members")
                            .header("Authorization", accessToken))
                    .andExpect(status().isOk())
                    .andDo(document("member/find",
                            preprocessRequest(prettyPrint()),
                            responseFields(
                                    fieldWithPath("name").description("사용자 이름"),
                                    fieldWithPath("email").description("사용자 이메일"),
                                    fieldWithPath("avatar").description("사용자 아바타 경로")
                            )
                    ));
        }
    }
}
