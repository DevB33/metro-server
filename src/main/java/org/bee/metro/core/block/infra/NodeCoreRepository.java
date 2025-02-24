package org.bee.metro.core.block.infra;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.block.domain.node.Node;
import org.bee.metro.core.block.domain.node.NodeRepository;
import org.bee.metro.core.block.entity.NodeEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NodeCoreRepository implements NodeRepository {

    private final NodeJpaRepository nodeJpaRepository;

    @Override
    public Node save(Node node) {
        NodeEntity nodeEntity = NodeEntity.from(node);
        NodeEntity savedNodeEntity = nodeJpaRepository.save(nodeEntity);
        return Node.fromEntity(savedNodeEntity);
    }

    @Override
    public List<Node> findByBlockId(UUID blockId) {
        List<NodeEntity> nodeEntityList = nodeJpaRepository.findByBlockId(blockId);
        return nodeEntityList.stream()
                .map(Node::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Node> findByDocumentId(UUID documentId) {
        List<NodeEntity> nodeEntityList = nodeJpaRepository.findByDocumentIdOrderByOrder(documentId);
        return nodeEntityList.stream()
                .map(Node::fromEntity)
                .collect(Collectors.toList());
    }
}
