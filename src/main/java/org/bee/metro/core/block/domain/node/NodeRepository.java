package org.bee.metro.core.block.domain.node;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NodeRepository {
    Node save(Node node);
    List<Node> findByBlockId(UUID blockId);
    List<Node> findByDocumentId(UUID documentId);
    Optional<Node> findById(UUID id);
    void deleteByDoucmentId(UUID documentId);
    void deleteByBlockId(UUID blockId);
}
