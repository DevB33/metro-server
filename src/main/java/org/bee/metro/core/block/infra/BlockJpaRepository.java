package org.bee.metro.core.block.infra;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bee.metro.core.block.entity.BlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockJpaRepository extends JpaRepository<BlockEntity, UUID> {
    Optional<BlockEntity> findById(UUID blockId);
    List<BlockEntity> findByDocumentIdOrderByOrder(UUID documentId);
    Boolean existsByDocumentIdAndOrderBetween(UUID blockId, Long startOrder, Long endOrder);
    void deleteByDocumentIdAndOrderBetween(UUID documentId, Long startOrder, Long endOrder);
    void deleteByDocumentId(UUID documentId);
}
