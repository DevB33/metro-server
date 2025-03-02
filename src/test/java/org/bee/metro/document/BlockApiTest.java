package org.bee.metro.document;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.bee.metro.context.DocumentTest;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.block.BlockType;
import org.bee.metro.core.block.dto.BlockCreationRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class BlockApiTest extends DocumentTest {

    @Nested
    class 블록_생성 {

        @Test
        void 블록_생성에_성공하면_200을_반환한다() throws Exception {
            BlockCreationRequest request = new BlockCreationRequest(
                    UUID.randomUUID(),
                    "TEXT",
                    1L
            );
            Block block = Block.builder()
                    .id(UUID.randomUUID())
                    .type(BlockType.TEXT)
                    .order(1L)
                    .documentId(UUID.randomUUID())
                    .memberId(randomId)
                    .build();
            given(blockService.createBlock(any(), any(), any(), any())).willReturn(block);

            mockMvc.perform(post("/blocks")
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andDo(document("block/create",
                            preprocessRequest(prettyPrint()),
                            requestFields(
                                    fieldWithPath("documentId").description("문서 ID"),
                                    fieldWithPath("type").description("블록 타입"),
                                    fieldWithPath("upperOrder").description("블록 순서")
                            ),
                            responseFields(
                                    fieldWithPath("id").description("생성된 블록 ID")
                            )
                    ));
        }
    }
}