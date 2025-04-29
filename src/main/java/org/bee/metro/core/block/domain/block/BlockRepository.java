package org.bee.metro.core.block.domain.block;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlockRepository {
    Block save(Block block);
    Optional<Block> findById(UUID blockId);
    List<Block> findByDocumentId(UUID documentId);
    Boolean existsByDocumentIdAndOrderBetween(UUID documentId, Long startOrder, Long endOrder);
    void deleteByDocumentIdAndOrderBetween(UUID documentId, Long startOrder, Long endOrder);
    void deleteByDocumentId(UUID documentId);
}
