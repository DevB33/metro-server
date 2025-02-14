package org.bee.metro.core.block.domain;

import java.util.List;
import java.util.UUID;

public interface BlockRepository {
    List<Block> findByDocumentId(UUID documentId);
}
