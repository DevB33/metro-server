package org.bee.metro.core.document.dto;

import java.util.List;
import java.util.Optional;
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
        List<String> tags = Optional.ofNullable(document.getTag())
                .map(tag -> List.of(tag.split(TAG_SEPARATOR)))
                .orElseGet(List::of);

        return new DetailDocumentPayload(
                document.getTitle(),
                document.getIcon(),
                tags,
                document.getCover(),
                blocksInDocument
        );
    }
}
