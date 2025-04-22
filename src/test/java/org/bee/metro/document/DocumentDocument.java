package org.bee.metro.document;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.bee.metro.context.DocumentTest;
import org.bee.metro.core.document.domain.Document;
import org.bee.metro.core.document.domain.LineColor;
import org.bee.metro.core.document.domain.Tag;
import org.bee.metro.core.document.dto.DetailDocumentPayload;
import org.bee.metro.core.document.dto.DocumentCreationRequest;
import org.bee.metro.core.document.dto.DocumentTagUpdateRequest;
import org.bee.metro.core.document.dto.DocumentTagsUpdateRequest;
import org.bee.metro.core.document.dto.DocumentTreeNode;
import org.bee.metro.core.document.dto.DocumentUpdateRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;

@AutoConfigureMockMvc
public class DocumentDocument extends DocumentTest {

    @Nested
    class 문서_리스트_조회 {

        @Test
        void 문서_리스트_조회가_성공하면_200을_반환한다() throws Exception {
            List<DocumentTreeNode> documentTreeNodes = List.of(
                    new DocumentTreeNode(UUID.randomUUID(), "name1", "icon1", Collections.emptyList()));
            given(documentService.getDocumentsByOwnerId(any())).willReturn(documentTreeNodes);

            mockMvc.perform(get("/notes")
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

    @Nested
    class 문서_생성 {

        @Test
        void 문서_생성이_성공하면_200을_반환한다() throws Exception {
            Document document = Document.builder()
                    .id(UUID.randomUUID())
                    .ownerId(randomId)
                    .parentId(UUID.randomUUID())
                    .build();
            given(documentService.createDocument(any(), any())).willReturn(document);

            DocumentCreationRequest documentCreationRequest = new DocumentCreationRequest(UUID.randomUUID());
            mockMvc.perform(post("/notes")
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(documentCreationRequest)))
                    .andExpect(status().isOk())
                    .andDo(document("document/create",
                            preprocessRequest(prettyPrint()),
                            requestFields(
                                    fieldWithPath("parentId").description("부모 문서 ID")
                            ),
                            responseFields(
                                    fieldWithPath("id").description("생성된 문서 ID")
                            )
                    ));
        }
    }

    @Nested
    class 문서_삭제 {

        @Test
        void 문서_삭제가_성공하면_200을_반환한다() throws Exception {
            UUID documentId = UUID.randomUUID();
            mockMvc.perform(delete("/notes/" + documentId)
                            .header("Authorization", accessToken))
                    .andExpect(status().isOk())
                    .andDo(document("document/delete",
                            preprocessRequest(prettyPrint())
                    ));
        }
    }

    @Nested
    class 문서_상세_조회 {

        @Test
        void 문서_상세_조회가_성공하면_200을_반환한다() throws Exception {
            DetailDocumentPayload detailDocumentPayload = new DetailDocumentPayload(
                    "title",
                    "icon",
                    List.of(new Tag("tag", LineColor.LINE_ONE)),
                    "cover",
                    Collections.emptyList()
            );
            given(documentService.findDocumentById(any(), any())).willReturn(detailDocumentPayload);

            UUID documentId = UUID.randomUUID();
            mockMvc.perform(get("/notes/" + documentId)
                            .header("Authorization", accessToken))
                    .andExpect(status().isOk())
                    .andDo(document("document/detail",
                                    preprocessRequest(prettyPrint()),
                                    responseFields(
                                            fieldWithPath("title").description("문서 제목"),
                                            fieldWithPath("icon").description("문서 아이콘"),
                                            fieldWithPath("tags[].name").description("문서 태그 목록"),
                                            fieldWithPath("tags[].color").description("문서 태그 색상"),
                                            fieldWithPath("cover").description("문서 커버 이미지"),
                                            fieldWithPath("blocks").description("문서 블록 목록")
                                    )
                            )
                    );
        }
    }

    @Nested
    class 문서_부분_수정 {

        @Test
        void 문서_부분_수정에_성공하면_200을_반환한다() throws Exception {
            DocumentUpdateRequest documentUpdateRequest = new DocumentUpdateRequest("value");

            String fieldType = "title";
            UUID documentId = UUID.randomUUID();
            mockMvc.perform(patch("/notes/" + documentId + "/" + fieldType)
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(documentUpdateRequest)))
                    .andExpect(status().isOk())
                    .andDo(document("document/update",
                            preprocessRequest(prettyPrint()),
                            requestFields(
                                    fieldWithPath("value").description("수정할 값")
                            )
                    ));
        }
    }

    @Nested
    class 문서_태그_수정 {

        @Test
        void 문서_태그_수정에_성공하면_200을_반환한다() throws Exception {
            DocumentTagUpdateRequest tagUpdateRequest1 = new DocumentTagUpdateRequest("tag", "line_one");
            DocumentTagUpdateRequest tagUpdateRequest2 = new DocumentTagUpdateRequest("tag", "line_two");
            DocumentTagsUpdateRequest tagsUpdateRequest = new DocumentTagsUpdateRequest(
                    List.of(tagUpdateRequest1, tagUpdateRequest2));

            UUID documentId = UUID.randomUUID();
            mockMvc.perform(patch("/notes/" + documentId + "/tags")
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(tagsUpdateRequest)))
                    .andExpect(status().isOk())
                    .andDo(document("document/update-tags",
                            preprocessRequest(prettyPrint()),
                            requestFields(
                                    fieldWithPath("tags[].name").description("태그 이름"),
                                    fieldWithPath("tags[].color").description("태그 색상")
                            )
                    ));
        }
    }
}
