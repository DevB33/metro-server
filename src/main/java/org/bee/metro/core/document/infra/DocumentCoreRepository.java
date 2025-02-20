package org.bee.metro.core.document.infra;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.document.common.DocumentFieldType;
import org.bee.metro.core.document.domain.Document;
import org.bee.metro.core.document.domain.DocumentRepository;
import org.bee.metro.core.document.domain.Tag;
import org.bee.metro.core.document.entity.DocumentEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DocumentCoreRepository implements DocumentRepository {

    private final DocumentJpaRepository documentJpaRepository;
    private final DocumentQueryDslRepository documentQueryDslRepository;

    @Override
    public Document save(Document document) {
        DocumentEntity documentEntity = DocumentEntity.from(document);
        DocumentEntity savedDocumentEntity = documentJpaRepository.save(documentEntity);
        return Document.fromEntity(savedDocumentEntity);
    }

    @Override
    public Optional<Document> findById(UUID id) {
        Optional<DocumentEntity> documentEntity = documentJpaRepository.findById(id);
        return documentEntity.map(Document::fromEntity);
    }

    @Override
    public List<Document> findByOwnerId(UUID ownerId) {
        List<DocumentEntity> documentEntities = documentJpaRepository.findByOwnerId(ownerId);
        return documentEntities.stream()
                .map(Document::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Document> findByParentId(UUID parentId) {
        List<DocumentEntity> documentEntities = documentJpaRepository.findByParentId(parentId);
        return documentEntities.stream()
                .map(Document::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        documentJpaRepository.deleteById(id);
    }

    @Override
    public void updateField(UUID id, DocumentFieldType type, String value) {
        documentQueryDslRepository.updateDocument(id, type, value);
    }

    @Override
    public void updateTags(UUID id, List<Tag> tags) {
        documentQueryDslRepository.updateTags(id, tags);
    }
}
