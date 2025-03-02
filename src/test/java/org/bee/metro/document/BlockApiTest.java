package org.bee.metro.document;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bee.metro.context.DocumentTest;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.block.BlockType;
import org.bee.metro.core.block.domain.node.Node;
import org.bee.metro.core.block.dto.BlockCreationRequest;
import org.bee.metro.core.block.dto.BlockOrderUpdateRequest;
import org.bee.metro.core.block.dto.DetailBlockPayload;
import org.bee.metro.core.block.dto.DetailBlocksRequest;
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

    @Nested
    class 블록_리스트_조회 {

        @Test
        void 블록_리스트_조회에_성공하면_200을_반환한다() throws Exception {
            UUID documentId = UUID.randomUUID();
            UUID blockId = UUID.randomUUID();
            Block block = Block.builder()
                    .id(blockId)
                    .type(BlockType.TEXT)
                    .order(1L)
                    .documentId(UUID.randomUUID())
                    .memberId(randomId)
                    .build();

            Node node = Node.builder()
                    .id(UUID.randomUUID())
                    .content("content")
                    .style(Map.of("key", "value"))
                    .order(1L)
                    .documentId(documentId)
                    .blockId(blockId)
                    .build();

            DetailBlockPayload payload = new DetailBlockPayload(block, List.of(node));
            given(blockService.findByDocumentId(any())).willReturn(List.of(payload));

            DetailBlocksRequest request = new DetailBlocksRequest(UUID.randomUUID());
            mockMvc.perform(get("/blocks")
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andDo(document("block/list",
                            preprocessRequest(prettyPrint()),
                            requestFields(
                                    fieldWithPath("documentId").description("문서 ID")
                            ),
                            responseFields(
                                    fieldWithPath("blocks").description("Block 리스트"),
                                    fieldWithPath("blocks[].block.id").description("블록 ID"),
                                    fieldWithPath("blocks[].block.type").description("블록 타입"),
                                    fieldWithPath("blocks[].block.order").description("블록 순서"),
                                    fieldWithPath("blocks[].block.documentId").description("문서 ID"),
                                    fieldWithPath("blocks[].block.memberId").description("회원 ID"),
                                    fieldWithPath("blocks[].nodes").description("블록에 포함된 노드 목록"),
                                    fieldWithPath("blocks[].nodes[].id").description("노드 ID"),
                                    fieldWithPath("blocks[].nodes[].content").description("노드 내용"),
                                    fieldWithPath("blocks[].nodes[].style").description("노드 스타일"),
                                    fieldWithPath("blocks[].nodes[].style.key").description("스타일 키"),
                                    fieldWithPath("blocks[].nodes[].order").description("노드 순서"),
                                    fieldWithPath("blocks[].nodes[].blockId").description("블록 ID"),
                                    fieldWithPath("blocks[].nodes[].documentId").description("문서 ID")
                            )
                    ));
        }
    }

    @Nested
    class 블록_순서_변경 {

        @Test
        void 블록_순서_변경에_성공하면_200을_반환한다() throws Exception {
            UUID documentId = UUID.randomUUID();
            BlockOrderUpdateRequest request = new BlockOrderUpdateRequest(
                    documentId,
                    1L,
                    2L,
                    3L
            );

            mockMvc.perform(patch("/blocks/order")
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andDo(document("block/update-order",
                            preprocessRequest(prettyPrint()),
                            requestFields(
                                    fieldWithPath("documentId").description("문서 ID"),
                                    fieldWithPath("startOrder").description("시작 순서"),
                                    fieldWithPath("endOrder").description("끝 순서"),
                                    fieldWithPath("upperOrder").description("상위 순서")
                            )
                    ));
        }
    }

}