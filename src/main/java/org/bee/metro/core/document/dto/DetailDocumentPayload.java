package org.bee.metro.core.document.dto;

import java.util.List;
import org.bee.metro.core.block.domain.Block;
import org.bee.metro.core.document.domain.Document;

public record DetailDocumentPayload(
        String title,
        String icon,
        List<String> tags,
        String cover,
        List<Block> blocks
) {

    private static final String TAG_SEPARATOR = "#";

    public static DetailDocumentPayload createByDocumentAndBlocks(Document document, List<Block> blocksInDocument) {
        List<String> tags = List.of(document.getTag().split(TAG_SEPARATOR));

        return new DetailDocumentPayload(
                document.getTitle(),
                document.getIcon(),
                tags,
                document.getCover(),
                blocksInDocument
        );
    }
}
