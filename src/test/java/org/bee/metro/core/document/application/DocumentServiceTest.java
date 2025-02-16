package org.bee.metro.core.document.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.UUID;
import org.bee.metro.context.ServiceTest;
import org.bee.metro.core.document.common.DocumentFieldType;
import org.bee.metro.core.document.domain.Document;
import org.bee.metro.core.document.dto.DetailDocumentPayload;
import org.bee.metro.core.document.dto.DocumentTreeNode;
import org.bee.metro.global.exception.type.BadRequestException;
import org.bee.metro.global.exception.type.NotFoundException;
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

    @Nested
    class createDocument_메서드는 {

        @Test
        void 문서를_생성한다() {
            UUID ownerId = UUID.randomUUID();
            UUID parentId = UUID.randomUUID();
            Document document = documentService.createDocument(ownerId, parentId);

            Document savedDocument = documentRepository.findById(document.getId()).orElseThrow();
            assertThat(savedDocument.getId()).isNotNull();
            assertThat(savedDocument.getOwnerId()).isEqualTo(ownerId);
        }

        @Test
        void 부모_문서가_없어도_문서를_생성한다() {
            UUID ownerId = UUID.randomUUID();
            Document document = documentService.createDocument(ownerId, null);

            Document savedDocument = documentRepository.findById(document.getId()).orElseThrow();
            assertThat(savedDocument.getId()).isNotNull();
            assertThat(savedDocument.getOwnerId()).isEqualTo(ownerId);
        }
    }

    @Nested
    class deleteDocument_메서드는 {

        @Test
        void 문서를_삭제한다() {
            UUID ownerId = UUID.randomUUID();
            UUID parentId = UUID.randomUUID();
            Document document = documentService.createDocument(ownerId, parentId);

            documentService.deleteDocument(ownerId, document.getId());

            assertThat(documentRepository.findById(document.getId())).isEmpty();
        }

        @Test
        void 해당_문서가_없다면_예외가_발생한다() {
            UUID ownerId = UUID.randomUUID();
            UUID documentId = UUID.randomUUID();

            assertThatThrownBy(() -> documentService.updateDocument(ownerId, documentId, DocumentFieldType.TITLE, "updated title"))
                    .isInstanceOf(NotFoundException.class);
        }

        @Test
        void 소유자가_아니라면_예외가_발생한다() {
            UUID ownerId = UUID.randomUUID();
            UUID parentId = UUID.randomUUID();
            Document document = documentService.createDocument(ownerId, parentId);

            UUID anotherOwnerId = UUID.randomUUID();
            assertThatThrownBy(() -> documentService.deleteDocument(anotherOwnerId, document.getId()))
                    .isInstanceOf(BadRequestException.class);
        }

        @Test
        void 하위_문서도_함께_삭제한다() {
            UUID ownerId = UUID.randomUUID();
            UUID parentId = UUID.randomUUID();
            Document document = documentService.createDocument(ownerId, parentId);
            Document childDocument1 = documentService.createDocument(ownerId, document.getId());
            Document childDocument2 = documentService.createDocument(ownerId, document.getId());

            documentService.deleteDocument(ownerId, document.getId());

            assertThat(documentRepository.findById(document.getId())).isEmpty();
            assertThat(documentRepository.findById(childDocument1.getId())).isEmpty();
            assertThat(documentRepository.findById(childDocument2.getId())).isEmpty();
        }
    }

    @Nested
    class findDocumentById_메서드는 {

        @Test
        void 문서를_조회한다() {
            UUID ownerId = UUID.randomUUID();
            UUID parentId = UUID.randomUUID();
            Document document = documentService.createDocument(ownerId, parentId);

            DetailDocumentPayload foundDocument = documentService.findDocumentById(ownerId, document.getId());

            assertAll(
                    () -> assertThat(foundDocument.title()).isEqualTo(document.getTitle()),
                    () -> assertThat(foundDocument.icon()).isEqualTo(document.getIcon()),
                    () -> assertThat(foundDocument.cover()).isEqualTo(document.getCover())
            );
        }

        @Test
        void 문서가_없다면_예외가_발생한다() {
            UUID ownerId = UUID.randomUUID();
            UUID documentId = UUID.randomUUID();

            assertThatThrownBy(() -> documentService.findDocumentById(ownerId, documentId))
                    .isInstanceOf(NotFoundException.class);
        }

        @Test
        void 문서_조회_권한이_없다면_예외가_발생한다() {
            UUID ownerId = UUID.randomUUID();
            UUID parentId = UUID.randomUUID();
            Document document = documentService.createDocument(ownerId, parentId);

            UUID notOwnerId = UUID.randomUUID();
            assertThatThrownBy(() -> documentService.findDocumentById(notOwnerId, document.getId()))
                    .isInstanceOf(BadRequestException.class);
        }
    }

    @Nested
    class updateDocument_메서드는 {

        @Test
        void 문서의_필드를_수정한다() {
            UUID ownerId = UUID.randomUUID();
            UUID parentId = UUID.randomUUID();
            Document document = documentService.createDocument(ownerId, parentId);

            documentService.updateDocument(ownerId, document.getId(), DocumentFieldType.TITLE, "updated title");

            Document updatedDocument = documentRepository.findById(document.getId()).orElseThrow();
            assertThat(updatedDocument.getTitle()).isEqualTo("updated title");
        }

        @Test
        void 해당_문서가_없다면_예외가_발생한다() {
            UUID ownerId = UUID.randomUUID();
            UUID documentId = UUID.randomUUID();

            assertThatThrownBy(() -> documentService.updateDocument(ownerId, documentId, DocumentFieldType.TITLE, "updated title"))
                    .isInstanceOf(NotFoundException.class);
        }

        @Test
        void 문서_수정_권한이_없다면_예외가_발생한다() {
            UUID ownerId = UUID.randomUUID();
            UUID parentId = UUID.randomUUID();
            Document document = documentService.createDocument(ownerId, parentId);

            UUID notOwnerId = UUID.randomUUID();
            assertThatThrownBy(() -> documentService.updateDocument(notOwnerId, document.getId(), DocumentFieldType.TITLE, "updated title"))
                    .isInstanceOf(BadRequestException.class);
        }
    }
}