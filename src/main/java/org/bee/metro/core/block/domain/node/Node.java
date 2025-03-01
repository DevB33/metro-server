package org.bee.metro.core.block.domain.node;

import static org.bee.metro.core.block.domain.block.Block.ERROR_IS_INVALID_BLOCK_ORDER;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.bee.metro.core.block.entity.NodeEntity;
import org.bee.metro.core.block.exception.BlockErrorCode;
import org.bee.metro.global.exception.type.BadRequestException;

@Getter
public class Node {

    private static final String ERROR_IS_INVALID_BLOCK_ID = "블록의 문서 ID는 필수입니다.";
    public static final String ERROR_IS_INVALID_DOCUMENT_ID = "문서 ID는 필수입니다.";

    private final UUID id;
    private final String content;
    private final Map<String, String> style;
    private final Long order;
    private final UUID blockId;
    private final UUID documentId;

    @Builder
    public Node(UUID id, String content, Map<String, String> style, Long order, UUID blockId, UUID documentId) {
        validateOrder(order);
        validateBlockId(blockId);
        validateDocumentId(documentId);

        this.id = id;
        this.content = content;
        this.style = style;
        this.order = order;
        this.blockId = blockId;
        this.documentId = documentId;
    }

    private void validateOrder(Long order) {
        if (order < 0L) {
            throw new BadRequestException(ERROR_IS_INVALID_BLOCK_ORDER.formatted(order), BlockErrorCode.INVALID_ORDER);
        }
    }

    private void validateBlockId(UUID blockId) {
        if (blockId == null) {
            throw new BadRequestException(ERROR_IS_INVALID_BLOCK_ID, BlockErrorCode.INVALID_BLOCK_ID);
        }
    }

    private void validateDocumentId(UUID documentId) {
        if (documentId == null) {
            throw new BadRequestException(ERROR_IS_INVALID_DOCUMENT_ID, BlockErrorCode.INVALID_DOCUMENT_ID);
        }
    }

    public static Node fromEntity(NodeEntity nodeEntity) {
        Map<String, String> nodeStyle = convertStyleStringToMap(nodeEntity.getStyle());
        return Node.builder()
            .id(nodeEntity.getId())
            .content(nodeEntity.getContent())
            .style(nodeStyle)
            .order(nodeEntity.getOrder())
            .blockId(nodeEntity.getBlockId())
            .documentId(nodeEntity.getDocumentId())
            .build();
    }

    private static Map<String, String> convertStyleStringToMap(String style) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(style, Map.class);
        } catch (Exception e) {
            throw new BadRequestException("스타일 정보를 변환하는데 실패했습니다. 스타일: %s".formatted(style),
                    BlockErrorCode.INVALID_CONVERT_STYLE);
        }
    }

    public Node updateContent(String content) {
        return Node.builder()
            .id(this.id)
            .content(content)
            .style(this.style)
            .order(this.order)
            .blockId(this.blockId)
            .documentId(this.documentId)
            .build();
    }

    public Node updateStyle(Map<String, String> style) {
        return Node.builder()
            .id(this.id)
            .content(this.content)
            .style(style)
            .order(this.order)
            .blockId(this.blockId)
            .documentId(this.documentId)
            .build();
    }

    public boolean isNotOwner(UUID memberId) {
        // TODO: 추후 노드 도메인 내 memberId 추가 구현
        return false;
    }
}
