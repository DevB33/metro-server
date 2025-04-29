package org.bee.metro.core.block.dto;

import java.util.List;
import org.bee.metro.core.block.domain.block.InnerNode;

public record BlockNodesUpdateRequest(
        List<InnerNode> nodes
) {
}
