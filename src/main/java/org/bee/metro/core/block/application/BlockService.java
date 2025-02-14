package org.bee.metro.core.block.application;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.block.domain.Block;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockService {

    public List<Block> findByDocumentId(UUID documentId) {
        return Collections.emptyList();
    }
}
