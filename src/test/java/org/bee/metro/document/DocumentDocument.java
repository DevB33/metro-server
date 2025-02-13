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

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.bee.metro.context.DocumentTest;
import org.bee.metro.core.document.dto.DocumentTreeNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@AutoConfigureMockMvc
public class DocumentDocument extends DocumentTest {

    @Nested
    class 문서_리스트_조회 {

        @Test
        void 문서_리스트_조회가_성공하면_200을_반환한다() throws Exception {
            List<DocumentTreeNode> documentTreeNodes = List.of(
                    new DocumentTreeNode(UUID.randomUUID(), "name1", "icon1", Collections.emptyList()));
            given(documentService.getDocumentsByOwnerId(any())).willReturn(documentTreeNodes);

            String accessToken = "Bearer " + refreshTokenProvider.generateToken(randomId);
            mockMvc.perform(get("/documents")
                            .header("Authorization", accessToken))
                    .andExpect(status().isOk())
                    .andDo(document("document/list",
                            preprocessRequest(prettyPrint()),
                            responseFields(
                                    fieldWithPath("node").description("문서 목록"),
                                    fieldWithPath("node[].id").description("문서 ID"),
                                    fieldWithPath("node[].title").description("문서 제목"),
                                    fieldWithPath("node[].icon").description("문서 아이콘"),
                                    fieldWithPath("node[].children").description("하위 문서 목록")
                            )
                    ));
        }
    }
}
