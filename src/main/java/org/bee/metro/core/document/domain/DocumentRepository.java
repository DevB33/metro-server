package org.bee.metro.core.document.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bee.metro.core.document.common.DocumentFieldType;

public interface DocumentRepository {
    Document save(Document document);
    Optional<Document> findById(UUID id);
    List<Document> findByOwnerId(UUID ownerId);
    List<Document> findByParentId(UUID parentId);

    void deleteById(UUID id);
    void updateField(UUID id, DocumentFieldType type, String value);
    void updateTags(UUID id, List<Tag> tags);
}
