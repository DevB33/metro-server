package org.bee.metro.core.block.dto;

import java.util.UUID;

public record BlockCreationRequest(
        UUID noteId,
        String type,
        Long upperOrder
) {
}
