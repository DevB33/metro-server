package org.bee.metro.core.document.dto;

import java.util.List;
import java.util.UUID;

public record DocumentTreeNode(
        UUID id,
        String title,
        String icon,
        List<DocumentTreeNode> children
) {
}
