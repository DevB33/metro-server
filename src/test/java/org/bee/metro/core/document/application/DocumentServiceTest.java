package org.bee.metro.core.document.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.bee.metro.context.ServiceTest;
import org.bee.metro.core.document.domain.Document;
import org.bee.metro.core.document.dto.DocumentTreeNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DocumentServiceTest extends ServiceTest {

    @Nested
    class getDocumentsByOwnerId_메서드는 {

        @Test
        void 현재_사용자의_문서_목록을_트리_형태로_조회한다() {
            UUID ownerId = UUID.randomUUID();
            Document document1 = Document.builder()
                    .id(null)
                    .title(null)
                    .tag(null)
                    .icon(null)
                    .ownerId(ownerId)
                    .parentId(null)
                    .build();
            Document savedDocument1 = documentRepository.save(document1);

            Document document2 = Document.builder()
                    .id(null)
                    .title(null)
                    .tag(null)
                    .icon(null)
                    .ownerId(ownerId)
                    .parentId(savedDocument1.getId())
                    .build();
            documentRepository.save(document2);

            Document document3 = Document.builder()
                    .id(null)
                    .title(null)
                    .tag(null)
                    .icon(null)
                    .ownerId(ownerId)
                    .parentId(savedDocument1.getId())
                    .build();
            documentRepository.save(document3);

            DocumentTreeNode documentTreeNode = documentService.getDocumentsByOwnerId(ownerId).get(0);

            assertThat(documentTreeNode.id()).isEqualTo(savedDocument1.getId());
            assertThat(documentTreeNode.children().size()).isEqualTo(2);
        }
    }
}