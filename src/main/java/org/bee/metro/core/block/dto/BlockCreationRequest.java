package org.bee.metro.core.block.dto;

import java.util.List;
import java.util.UUID;
import org.bee.metro.core.block.domain.block.InnerNode;

public record BlockCreationRequest(
        UUID noteId,
        String type,
        Long upperOrder,
        List<InnerNode> nodes
) {
}
