package org.bee.metro.core.block.dto;

import java.util.UUID;

public record BlockCreationRequest(
        UUID documentId,
        String type,
        Long upperOrder
) {
}
