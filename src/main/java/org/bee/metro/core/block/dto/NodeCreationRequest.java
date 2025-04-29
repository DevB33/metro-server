package org.bee.metro.core.block.dto;

import java.util.Map;
import java.util.UUID;

public record NodeCreationRequest(
        UUID blockId,
        UUID noteId,
        String content,
        Long order,
        Map<String, String> style
) {
}
