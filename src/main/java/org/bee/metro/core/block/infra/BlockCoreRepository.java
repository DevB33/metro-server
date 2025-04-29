package org.bee.metro.core.block.infra;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.block.BlockRepository;
import org.bee.metro.core.block.entity.BlockEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BlockCoreRepository implements BlockRepository {

    private final BlockJpaRepository blockJpaRepository;

    @Override
    public Block save(Block block) {
        BlockEntity blockEntity = BlockEntity.from(block);
        BlockEntity savedBlockEntity = blockJpaRepository.save(blockEntity);
        return Block.fromEntity(savedBlockEntity);
    }

    @Override
    public Optional<Block> findById(UUID blockId) {
        Optional<BlockEntity> blockEntity = blockJpaRepository.findById(blockId);
        return blockEntity.map(Block::fromEntity);
    }

    @Override
    public List<Block> findByDocumentId(UUID documentId) {
        List<BlockEntity> blockEntityList = blockJpaRepository.findByDocumentIdOrderByOrder(documentId);
        return blockEntityList.stream()
                .map(Block::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean existsByDocumentIdAndOrderBetween(UUID documentId, Long startOrder, Long endOrder) {
        return blockJpaRepository.existsByDocumentIdAndOrderBetween(documentId, startOrder, endOrder);
    }

    @Override
    public void deleteByDocumentIdAndOrderBetween(UUID documentId, Long startOrder, Long endOrder) {
        blockJpaRepository.deleteByDocumentIdAndOrderBetween(documentId, startOrder, endOrder);
    }

    @Override
    public void deleteByDocumentId(UUID documentId) {
        blockJpaRepository.deleteByDocumentId(documentId);
    }
}
