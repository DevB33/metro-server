package org.bee.metro.core.document.dto;

import java.util.UUID;

public record DocumentCreationAtListRequest(
        UUID parentId
) {
}
