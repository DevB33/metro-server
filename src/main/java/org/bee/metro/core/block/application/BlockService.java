package org.bee.metro.core.block.application;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.block.BlockRepository;
import org.bee.metro.core.block.domain.block.BlockType;
import org.bee.metro.core.block.domain.block.InnerNode;
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
                .nodes(new ArrayList<>())
                .build();
        Block savedBlock = blockRepository.save(block);
        // TODO: 이후 블록들 order 조정 로직 추가
        // createDefaultNode(savedBlock.getId(), documentId);
        return savedBlock;
    }

    private void createDefaultNode(UUID blockId, UUID documentId) {
        InnerNode node = InnerNode.builder().build();
        // nodeRepository.save(node);
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
        // TODO: 이후 노드들 order 조정 로직 추가
        return nodeRepository.save(node);
    }

    public List<DetailBlockPayload> findByDocumentId(UUID documentId) {
        List<Block> blockList = blockRepository.findByDocumentId(documentId);
        return blockList.stream()
                .map(block -> new DetailBlockPayload(block))
                .collect(Collectors.toList());
    }

    private List<DetailBlockPayload> createBlockList(List<Block> blockList, Map<UUID, List<Node>> nodeMapByBlockId) {
        List<DetailBlockPayload> detailBlockPayloadList = new ArrayList<>();
        blockList.forEach(block -> {
            List<Node> nodes = nodeMapByBlockId.get(block.getId());
            DetailBlockPayload detailBlockPayload = new DetailBlockPayload(block);
            detailBlockPayloadList.add(detailBlockPayload);
        });
        return detailBlockPayloadList;
    }

    private Node getNode(UUID nodeId) {
        return nodeRepository.findById(nodeId).orElseThrow(
                () -> new NotFoundException("해당 노드가 존재하지 않습니다.", BlockErrorCode.NOT_FOUND_NODE));
    }

    @Transactional
    public void updateNodeContent(UUID nodeId, UUID memberId, String content) {
        Node node = getNode(nodeId);
        if (node.isNotOwner(memberId)) {
            throw new BadRequestException("해당 노드의 수정 권한이 없습니다.", BlockErrorCode.UNAUTHORIZED);
        }

        Node updatedNode = node.updateContent(content);
        nodeRepository.save(updatedNode);
    }

    @Transactional
    public void updateNodeStyle(UUID nodeId, UUID memberId, Map<String, String> style) {
        Node node = getNode(nodeId);
        if (node.isNotOwner(memberId)) {
            throw new BadRequestException("해당 노드의 수정 권한이 없습니다.", BlockErrorCode.UNAUTHORIZED);
        }

        Node updatedNode = node.updateStyle(style);
        nodeRepository.save(updatedNode);
    }

    public Block getBlock(UUID blockId) {
        return blockRepository.findById(blockId).orElseThrow(
                () -> new NotFoundException("해당 블록이 존재하지 않습니다.", BlockErrorCode.NOT_FOUND_BLOCK));
    }

    @Transactional
    public void updateNodes(UUID memberId, UUID blockId, List<InnerNode> nodes) {
        Block block = getBlock(blockId);
        if (block.isNotOwner(memberId)) {
            throw new BadRequestException("해당 블록의 수정 권한이 없습니다.", BlockErrorCode.UNAUTHORIZED);
        }

        Block updatedBlock = Block.builder()
                .id(block.getId())
                .type(block.getType())
                .order(block.getOrder())
                .documentId(block.getDocumentId())
                .memberId(block.getMemberId())
                .nodes(nodes)
                .build();
        blockRepository.save(updatedBlock);
    }

    @Transactional
    public void updateBlocksOrder(UUID documentId, UUID memberId, Long startOrder, Long endOrder, Long upperOrder) {
        Long range = endOrder - startOrder + 1;
        List<Block> blockList = blockRepository.findByDocumentId(documentId);

        blockList.stream().forEach(block -> {
            if (block.isNotOwner(memberId)) {
                throw new BadRequestException("해당 블록의 수정 권한이 없습니다.", BlockErrorCode.UNAUTHORIZED);
            }
        });

        if (existsBlockInRange(documentId, range, upperOrder)) {
            moveBlocksBack(upperOrder, blockList, range);
        }
        arrangeBlocks(startOrder, endOrder, upperOrder, blockList);
    }

    private void arrangeBlocks(Long startOrder, Long endOrder, Long upperOrder, List<Block> blockList) {
        for (Block block: blockList) {
            if (startOrder <= block.getOrder() && block.getOrder() <= endOrder) {
                Block updatedBlock = block.updateOrder(upperOrder + block.getOrder() - startOrder + 1);
                blockRepository.save(updatedBlock);
            }
        }
    }

    private void moveBlocksBack(Long upperOrder, List<Block> blockList, Long range) {
        for (Block block: blockList) {
            if (block.getOrder() > upperOrder) {
                Block updatedBlock = block.updateOrder(block.getOrder() + range);
                blockRepository.save(updatedBlock);
            }
        }
    }

    private Boolean existsBlockInRange(UUID documentId, Long range, Long upperOrder) {
        return blockRepository.existsByDocumentIdAndOrderBetween(
                documentId, upperOrder + 1, upperOrder + range);
    }

    @Transactional
    public void deleteBlockInRange(UUID documentId, UUID memberId, Long startOrder, Long endOrder) {
        List<Block> blockList = blockRepository.findByDocumentId(documentId);
        blockList.stream().forEach(block -> {
            if (block.isNotOwner(memberId)) {
                throw new BadRequestException("해당 블록의 수정 권한이 없습니다.", BlockErrorCode.UNAUTHORIZED);
            }
        });

        deleteNodeByBlockId(startOrder, endOrder, blockList);
        blockRepository.deleteByDocumentIdAndOrderBetween(documentId, startOrder, endOrder);
    }

    private void deleteNodeByBlockId(Long startOrder, Long endOrder, List<Block> blockList) {
        for (Block block: blockList) {
            if (startOrder <= block.getOrder() && block.getOrder() <= endOrder) {
                nodeRepository.deleteByBlockId(block.getId());
            }
        }
    }

    @Transactional
    public void deleteByDocumentId(UUID documentId, UUID memberId) {
        List<Block> blockList = blockRepository.findByDocumentId(documentId);
        blockList.stream().forEach(block -> {
            if (block.isNotOwner(memberId)) {
                throw new BadRequestException("해당 블록의 수정 권한이 없습니다.", BlockErrorCode.UNAUTHORIZED);
            }
        });

        nodeRepository.deleteByDoucmentId(documentId);
        blockRepository.deleteByDocumentId(documentId);
    }
}
