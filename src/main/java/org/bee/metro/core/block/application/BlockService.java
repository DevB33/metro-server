package org.bee.metro.core.block.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.block.BlockRepository;
import org.bee.metro.core.block.domain.block.BlockType;
import org.bee.metro.core.block.domain.node.Node;
import org.bee.metro.core.block.domain.node.NodeRepository;
import org.bee.metro.core.block.dto.DetailBlockPayload;
import org.bee.metro.core.block.exception.BlockErrorCode;
import org.bee.metro.global.exception.type.BadRequestException;
import org.bee.metro.global.exception.type.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;
    private final NodeRepository nodeRepository;

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
        createDefaultNode(savedBlock.getId(), documentId);
        return savedBlock;
    }

    private void createDefaultNode(UUID blockId, UUID documentId) {
        Node node = Node.builder()
                .id(null)
                .order(0L)
                .blockId(blockId)
                .documentId(documentId)
                .build();
        nodeRepository.save(node);
    }

    public Node createNode(UUID blockId, UUID documentId, String content, Long order, Map<String, String> style) {
        Node node = Node.builder()
                .id(null)
                .content(content)
                .style(style)
                .order(order)
                .blockId(blockId)
                .documentId(documentId)
                .build();
        return nodeRepository.save(node);
    }

    public List<DetailBlockPayload> findByDocumentId(UUID documentId) {
        List<Node> nodeList = nodeRepository.findByDocumentId(documentId);
        List<Block> blockList = blockRepository.findByDocumentId(documentId);

        Map<UUID, List<Node>> nodeMapByBlockId = nodeList.stream()
                .collect(Collectors.groupingBy(Node::getBlockId));
        return createBlockList(blockList, nodeMapByBlockId);
    }

    private List<DetailBlockPayload> createBlockList(List<Block> blockList, Map<UUID, List<Node>> nodeMapByBlockId) {
        List<DetailBlockPayload> detailBlockPayloadList = new ArrayList<>();
        blockList.forEach(block -> {
            List<Node> nodes = nodeMapByBlockId.get(block.getId());
            DetailBlockPayload detailBlockPayload = new DetailBlockPayload(block, nodes);
            detailBlockPayloadList.add(detailBlockPayload);
        });
        return detailBlockPayloadList;
    }

    private Node getNode(UUID nodeId) {
        return nodeRepository.findById(nodeId).orElseThrow(
                () -> new NotFoundException("해당 노드가 존재하지 않습니다.", BlockErrorCode.NOT_FOUND_NODE));
    }

    public void updateNodeContent(UUID nodeId, UUID memberId, String content) {
        Node node = getNode(nodeId);
        if (node.isNotOwner(memberId)) {
            throw new BadRequestException("해당 노드의 수정 권한이 없습니다.", BlockErrorCode.UNAUTHORIZED);
        }

        Node updatedNode = node.updateContent(content);
        nodeRepository.save(updatedNode);
    }

    public void updateNodeStyle(UUID nodeId, UUID memberId, Map<String, String> style) {
        Node node = getNode(nodeId);
        if (node.isNotOwner(memberId)) {
            throw new BadRequestException("해당 노드의 수정 권한이 없습니다.", BlockErrorCode.UNAUTHORIZED);
        }

        Node updatedNode = node.updateStyle(style);
        nodeRepository.save(updatedNode);
    }
}
