package org.bee.metro.core.block.dto;

import java.util.UUID;

public record BlockOrderUpdateRequest(
        UUID documentId,
        Long startOrder,
        Long endOrder,
        Long upperOrder
) {
}
