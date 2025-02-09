package org.bee.metro.core.document.infra;

import lombok.RequiredArgsConstructor;
import org.bee.metro.core.document.domain.Document;
import org.bee.metro.core.document.domain.DocumentRepository;
import org.bee.metro.core.document.entity.DocumentEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DocumentCoreRepository implements DocumentRepository {

    private final DocumentJpaRepository documentJpaRepository;

    @Override
    public Document save(Document document) {
        DocumentEntity documentEntity = DocumentEntity.from(document);
        DocumentEntity savedDocumentEntity = documentJpaRepository.save(documentEntity);
        return Document.fromEntity(savedDocumentEntity);
    }
}
