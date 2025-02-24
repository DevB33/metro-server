package org.bee.metro.core.document.dto;

import java.util.List;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.document.domain.Document;
import org.bee.metro.core.document.domain.Tag;

public record DetailDocumentPayload(
        String title,
        String icon,
        List<Tag> tags,
        String cover,
        List<Block> blocks
) {

    public static DetailDocumentPayload createByDocumentAndBlocks(Document document, List<Block> blocksInDocument) {
        return new DetailDocumentPayload(
                document.getTitle(),
                document.getIcon(),
                document.getTags(),
                document.getCover(),
                blocksInDocument
        );
    }
}
