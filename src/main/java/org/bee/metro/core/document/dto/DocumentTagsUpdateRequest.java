package org.bee.metro.core.document.dto;

import java.util.List;

public record DocumentTagsUpdateRequest(
        List<DocumentTagUpdateRequest> tags
) {
}
