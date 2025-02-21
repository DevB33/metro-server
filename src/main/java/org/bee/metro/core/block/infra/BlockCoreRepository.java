package org.bee.metro.core.block.infra;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.block.BlockRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BlockCoreRepository implements BlockRepository {

    private final BlockJpaRepository blockJpaRepository;

    @Override
    public Block save(Block block) {
        return null;
    }

    @Override
    public List<Block> findByDocumentId(UUID documentId) {
        return List.of();
    }
}
