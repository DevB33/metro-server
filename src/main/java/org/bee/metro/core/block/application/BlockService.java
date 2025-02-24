package org.bee.metro.core.block.application;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.block.BlockRepository;
import org.bee.metro.core.block.domain.block.BlockType;
import org.bee.metro.core.block.domain.node.Node;
import org.bee.metro.core.block.domain.node.NodeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;
    private final NodeRepository nodeRepository;

    public List<Block> findByDocumentId(UUID documentId) {
        return Collections.emptyList();
    }

    public Block createBlock(UUID memberId, UUID documentId, BlockType type, Long order) {
        Block block = Block.builder()
                .id(null)
                .type(type)
                .order(order)
                .documentId(documentId)
                .memberId(memberId)
                .build();
        Block savedBlock = blockRepository.save(block);
        // TODO: 이후 블록들 order 조정 로직 추가
        createDefaultNode(savedBlock.getId());
        return savedBlock;
    }

    private void createDefaultNode(UUID blockId) {
        Node node = Node.builder()
                .id(null)
                .order(0L)
                .blockId(blockId)
                .build();
        nodeRepository.save(node);
    }

    public Node createNode(UUID blockId, String content, Long order, Map<String, String> style) {
        Node node = Node.builder()
                .id(null)
                .content(content)
                .style(style)
                .order(order)
                .blockId(blockId)
                .build();
        return nodeRepository.save(node);
    }
}
