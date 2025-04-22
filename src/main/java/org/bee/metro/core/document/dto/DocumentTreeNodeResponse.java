package org.bee.metro.core.document.dto;

import java.util.List;

public record DocumentTreeNodeResponse(
        List<DocumentTreeNode> notes
) {
}
