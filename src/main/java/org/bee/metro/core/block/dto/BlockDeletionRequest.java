package org.bee.metro.core.block.dto;

import java.util.UUID;

public record BlockDeletionRequest(
        UUID noteId,
        Long startOrder,
        Long endOrder
) {
}
