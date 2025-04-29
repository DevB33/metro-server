package org.bee.metro.core.block.dto;

import java.util.UUID;

public record BlockOrderUpdateRequest(
        UUID noteId,
        Long startOrder,
        Long endOrder,
        Long upperOrder
) {
}
