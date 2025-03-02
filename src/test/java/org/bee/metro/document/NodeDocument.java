package org.bee.metro.document;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import java.util.UUID;
import org.bee.metro.context.DocumentTest;
import org.bee.metro.core.block.dto.NodeContentUpdateRequest;
import org.bee.metro.core.block.dto.NodeCreationRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class NodeDocument extends DocumentTest {

    @Nested
    class 노드_생성 {

        @Test
        void 노드_생성에_성공하면_200을_반환한다() throws Exception {
            NodeCreationRequest nodeCreationRequest = new NodeCreationRequest(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    "content",
                    1L,
                    Map.of("key", "value")
            );

            mockMvc.perform(post("/nodes")
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(nodeCreationRequest)))
                    .andExpect(status().isOk())
                    .andDo(document("node/create",
                            preprocessRequest(prettyPrint()),
                            requestFields(
                                    fieldWithPath("blockId").description("블록 ID"),
                                    fieldWithPath("documentId").description("문서 ID"),
                                    fieldWithPath("content").description("내용"),
                                    fieldWithPath("order").description("순서"),
                                    fieldWithPath("style").description("스타일"),
                                    fieldWithPath("style.key").description("스타일 키")
                            )
                    ));
        }
    }

    @Nested
    class 노드_내용_수정 {

        @Test
        void 노드_내용_수정에_성공하면_200을_반환한다() throws Exception {
            NodeContentUpdateRequest nodeContentUpdateRequest = new NodeContentUpdateRequest("content");

            UUID nodeId = UUID.randomUUID();
            mockMvc.perform(patch("/nodes/" + nodeId + "/content")
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(nodeContentUpdateRequest)))
                    .andExpect(status().isOk())
                    .andDo(document("node/update-content",
                            preprocessRequest(prettyPrint()),
                            requestFields(
                                    fieldWithPath("content").description("내용")
                            )
                    ));
        }
    }
}
