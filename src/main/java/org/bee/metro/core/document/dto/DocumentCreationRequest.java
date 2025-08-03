package org.bee.metro.core.document.dto;

import java.util.UUID;

public record DocumentCreationRequest(
        UUID parentId,
        Long upperOrder
) {
}
