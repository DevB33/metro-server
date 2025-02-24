package org.bee.metro.core.block.domain.node;

import java.util.List;
import java.util.UUID;

public interface NodeRepository {
    Node save(Node node);
    List<Node> findByBlockId(UUID blockId);
}
