package org.bee.metro.core.block.dto;

import java.util.List;

public record DetailBlocksResponse(
        List<DetailBlockPayload> blocks
) {
}
