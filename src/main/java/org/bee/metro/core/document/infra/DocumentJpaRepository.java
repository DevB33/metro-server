package org.bee.metro.core.document.infra;

import java.util.List;
import java.util.UUID;
import org.bee.metro.core.document.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentJpaRepository extends JpaRepository<DocumentEntity, UUID> {
    List<DocumentEntity> findByOwnerId(UUID ownerId);
    List<DocumentEntity> findByParentId(UUID parentId);
}
