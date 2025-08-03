package org.bee.metro.core.block.domain.block;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.bee.metro.core.block.entity.BlockEntity;
import org.bee.metro.core.block.exception.BlockErrorCode;
import org.bee.metro.global.exception.type.BadRequestException;

@Getter
public class Block {

    public static final String ERROR_IS_INVALID_BLOCK_ORDER = "블록의 순서는 0보다 작을 수 없습니다. 현재 순서: %s";
    public static final String ERROR_IS_INVALID_DOCUMENT_ID = "블록의 문서 ID는 필수입니다.";
    public static final String ERROR_IS_INVALID_MEMBER_ID = "사용자의 ID는 필수입니다.";

    private final UUID id;
    private final BlockType type;
    private final Long order;
    private final UUID documentId;
    private final UUID memberId;

    @Builder
    public Block(UUID id, BlockType type, Long order, UUID documentId, UUID memberId) {
        validateOrder(order);
        validateMemberId(memberId);

        this.id = id;
        this.type = type;
        this.order = order;
        this.documentId = documentId;
        this.memberId = memberId;
    }

    private void validateOrder(Long order) {
        if (order < 0L) {
            throw new BadRequestException(ERROR_IS_INVALID_BLOCK_ORDER.formatted(order), BlockErrorCode.INVALID_ORDER);
        }
    }

    private void validateDocumentId(UUID documentId) {
        if (documentId == null) {
            throw new BadRequestException(ERROR_IS_INVALID_DOCUMENT_ID, BlockErrorCode.INVALID_DOCUMENT_ID);
        }
    }

    private void validateMemberId(UUID memberId) {
        if (memberId == null) {
            throw new BadRequestException(ERROR_IS_INVALID_DOCUMENT_ID, BlockErrorCode.INVALID_MEMBER_ID);
        }
    }

    public static Block fromEntity(BlockEntity blockEntity) {
        return Block.builder()
            .id(blockEntity.getId())
            .type(blockEntity.getType())
            .order(blockEntity.getOrder())
            .documentId(blockEntity.getDocumentId())
            .memberId(blockEntity.getMemberId())
            .build();
    }

    public Block updateOrder(Long order) {
        return Block.builder()
            .id(this.id)
            .type(this.type)
            .order(order)
            .documentId(this.documentId)
            .memberId(this.memberId)
            .build();
    }

    public boolean isNotOwner(UUID memberId) {
        return !this.memberId.equals(memberId);
    }
}
