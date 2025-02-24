package org.bee.metro.core.block.dto;

import java.util.List;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.node.Node;

public record DetailBlockPayload(
        Block block,
        List<Node> nodes
) {
}
