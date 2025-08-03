package org.bee.metro.core.document.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bee.metro.core.document.domain.Document;

public record DocumentTreeNode(
        UUID id,
        String title,
        String icon,
        Long order,
        UUID parentId,
        List<DocumentTreeNode> children
) {

    public static DocumentTreeNode createByDocument(Document document) {
        return new DocumentTreeNode(
                document.getId(),
                document.getTitle(),
                document.getIcon(),
                document.getOrder(),
                document.getParentId(),
                new ArrayList<>()
        );
    }
}
