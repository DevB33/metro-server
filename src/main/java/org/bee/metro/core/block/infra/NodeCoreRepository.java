package org.bee.metro.core.block.infra;

import lombok.RequiredArgsConstructor;
import org.bee.metro.core.block.domain.node.Node;
import org.bee.metro.core.block.domain.node.NodeRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NodeCoreRepository implements NodeRepository {
    @Override
    public Node save(Node node) {
        return null;
    }
}
