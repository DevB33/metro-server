package org.bee.metro.core.document.domain;

import java.util.List;
import java.util.UUID;

public interface DocumentRepository {

    Document save(Document document);
    List<Document> findByOwnerId(UUID ownerId);
}
