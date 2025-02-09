package org.bee.metro.core.document.infra;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;
import org.bee.metro.context.RepositoryTest;
import org.bee.metro.core.document.domain.Document;
import org.bee.metro.core.document.domain.DocumentRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DocumentCoreRepositoryTest extends RepositoryTest {

    private final DocumentRepository documentRepository;

    @Autowired
    DocumentCoreRepositoryTest(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Nested
    class save_메서드는 {

        @Test
        void 문서를_저장한다() {
            Document document = Document.builder()
                    .id(null)
                    .title("title")
                    .tag("tag")
                    .icon("icon")
                    .cover("cover")
                    .parentId(UUID.randomUUID())
                    .ownerId(UUID.randomUUID())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Document savedDocument = documentRepository.save(document);

            assertThat(savedDocument).isNotNull();
        }
    }
}