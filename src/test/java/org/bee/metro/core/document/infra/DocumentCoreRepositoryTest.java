package org.bee.metro.core.document.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        void 문서를_생성한다() {
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

        @Test
        void 이미_존재하는_문서는_저장한다() {
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

            Document updatedDocument = Document.builder()
                    .id(savedDocument.getId())
                    .title("updated title")
                    .tag("updated tag")
                    .icon("updated icon")
                    .cover("updated cover")
                    .parentId(UUID.randomUUID())
                    .ownerId(UUID.randomUUID())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Document updatedSavedDocument = documentRepository.save(updatedDocument);

            assertAll(
                    () -> assertThat(updatedSavedDocument.getTitle()).isEqualTo("updated title"),
                    () -> assertThat(updatedSavedDocument.getTag()).isEqualTo("updated tag"),
                    () -> assertThat(updatedSavedDocument.getIcon()).isEqualTo("updated icon"),
                    () -> assertThat(updatedSavedDocument.getCover()).isEqualTo("updated cover")
            );
        }
    }

    @Nested
    class findById_메서드는 {

        @Test
        void 문서를_조회한다() {
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

            Document foundDocument = documentRepository.findById(savedDocument.getId()).get();

            assertThat(foundDocument).isNotNull();
        }

        @Test
        void 문서가_존재하지_않으면_null을_반환한다() {
            UUID id = UUID.randomUUID();
            assertThat(documentRepository.findById(id)).isEmpty();
        }
    }

    @Nested
    class findByOwnerId_메서드는 {

        @Test
        void 문서를_조회한다() {
            UUID ownerId = UUID.randomUUID();
            Document document = Document.builder()
                    .id(null)
                    .title("title")
                    .tag("tag")
                    .icon("icon")
                    .cover("cover")
                    .parentId(UUID.randomUUID())
                    .ownerId(ownerId)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            documentRepository.save(document);

            Document foundDocument = documentRepository.findByOwnerId(ownerId).get(0);

            assertThat(foundDocument).isNotNull();
        }
    }

    @Nested
    class findByParentId_메서드는 {

        @Test
        void 문서를_조회한다() {
            UUID parentId = UUID.randomUUID();
            Document document = Document.builder()
                    .id(null)
                    .title("title")
                    .tag("tag")
                    .icon("icon")
                    .cover("cover")
                    .parentId(parentId)
                    .ownerId(UUID.randomUUID())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            documentRepository.save(document);

            Document foundDocument = documentRepository.findByParentId(parentId).get(0);

            assertThat(foundDocument).isNotNull();
        }
    }

    @Nested
    class deleteById_메서드는 {

        @Test
        void 해당_문서를_삭제한다() {
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

            documentRepository.deleteById(savedDocument.getId());

            assertThat(documentRepository.findById(savedDocument.getId())).isEmpty();
        }
    }
}