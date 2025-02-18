package org.bee.metro.core.block.domain;

import java.util.UUID;
import lombok.Builder;

public class Block {

    private final UUID id;
    private final BlockType type;
    private final Long order;
    private final UUID documentId;

    @Builder
    public Block(UUID id, BlockType type, Long order, UUID documentId) {
        this.id = id;
        this.type = type;
        this.order = order;
        this.documentId = documentId;
    }
}
