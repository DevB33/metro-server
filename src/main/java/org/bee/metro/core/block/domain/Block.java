package org.bee.metro.core.block.domain;

import java.util.UUID;
import lombok.Builder;
import org.bee.metro.core.block.exception.BlockErrorCode;
import org.bee.metro.global.exception.type.BadRequestException;

public class Block {

    public static final String ERROR_IS_INVALID_BLOCK_ORDER = "블록의 순서는 0보다 작을 수 없습니다. 현재 순서: %s";
    private final UUID id;
    private final BlockType type;
    private final Long order;
    private final UUID documentId;

    @Builder
    public Block(UUID id, BlockType type, Long order, UUID documentId) {
        validateOrder(order);
        validateDocumentId(documentId);

        this.id = id;
        this.type = type;
        this.order = order;
        this.documentId = documentId;
    }

    private void validateOrder(Long order) {
        if (order < 0L) {
            throw new BadRequestException(ERROR_IS_INVALID_BLOCK_ORDER.formatted(order), BlockErrorCode.INVALID_ORDER);
        }
    }

    private void validateDocumentId(UUID documentId) {
    }
}
