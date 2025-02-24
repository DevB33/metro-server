package org.bee.metro.core.block.infra;

import java.util.List;
import java.util.UUID;
import org.bee.metro.core.block.entity.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeJpaRepository extends JpaRepository<NodeEntity, UUID> {
    List<NodeEntity> findByBlockId(UUID blockId);
    List<NodeEntity> findByDocumentIdOrderByOrder(UUID documentId);
}
