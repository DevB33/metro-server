package org.bee.metro.core.block.domain;

import static org.bee.metro.core.block.domain.Block.ERROR_IS_INVALID_BLOCK_ORDER;

import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.bee.metro.core.block.exception.BlockErrorCode;
import org.bee.metro.global.exception.type.BadRequestException;

@Getter
public class Node {

    private static final String ERROR_IS_INVALID_BLOCK_ID = "블록의 문서 ID는 필수입니다.";

    private final UUID id;
    private final String content;
    private final Map<String, String> style;
    private final Long order;
    private final UUID blockId;

    @Builder
    public Node(UUID id, String content, Map<String, String> style, Long order, UUID blockId) {
        validateOrder(order);
        validateBlockId(blockId);

        this.id = id;
        this.content = content;
        this.style = style;
        this.order = order;
        this.blockId = blockId;
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
}
