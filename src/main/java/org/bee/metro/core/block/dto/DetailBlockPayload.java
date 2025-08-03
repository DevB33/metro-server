package org.bee.metro.core.block.dto;

import java.util.List;
import java.util.UUID;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.block.BlockType;
import org.bee.metro.core.block.domain.block.InnerNode;

public record DetailBlockPayload(
        UUID id,
        BlockType type,
        List<InnerNode> nodes,
        Long order
) {
    public static DetailBlockPayload of(Block block) {
        return new DetailBlockPayload(block.getId(), block.getType(), block.getNodes(), block.getOrder());
    }
}
