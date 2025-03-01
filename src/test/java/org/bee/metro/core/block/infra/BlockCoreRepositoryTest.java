package org.bee.metro.core.block.infra;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.bee.metro.context.RepositoryTest;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.block.BlockRepository;
import org.bee.metro.core.block.domain.block.BlockType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BlockCoreRepositoryTest extends RepositoryTest {

    private final BlockRepository blockRepository;

    @Autowired
    BlockCoreRepositoryTest(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @Nested
    class save_메서드는 {

        @Test
        void 아이디가_없다면_블록을_생성한다() {
            BlockType type = BlockType.TEXT;
            Long order = 1L;
            UUID documentId = UUID.randomUUID();
            UUID memberId = UUID.randomUUID();

            Block block = Block.builder()
                    .id(null)
                    .type(type)
                    .order(order)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();

            Block savedBlock = blockRepository.save(block);

            assertThat(savedBlock.getId()).isNotNull();
            assertThat(savedBlock.getType()).isEqualTo(type);
            assertThat(savedBlock.getOrder()).isEqualTo(order);
            assertThat(savedBlock.getDocumentId()).isEqualTo(documentId);
        }

        @Test
        void 아이디가_존재하면_블록을_수정한다() {
            BlockType type = BlockType.TEXT;
            Long order = 1L;
            UUID documentId = UUID.randomUUID();
            UUID memberId = UUID.randomUUID();

            Block block = Block.builder()
                    .id(null)
                    .type(type)
                    .order(order)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();

            Block savedBlock = blockRepository.save(block);

            BlockType modifiedType = BlockType.IMAGE;
            Long modifiedOrder = 2L;
            UUID modifiedDocumentId = UUID.randomUUID();

            Block modifiedBlock = Block.builder()
                    .id(savedBlock.getId())
                    .type(modifiedType)
                    .order(modifiedOrder)
                    .documentId(modifiedDocumentId)
                    .memberId(memberId)
                    .build();

            Block updatedBlock = blockRepository.save(modifiedBlock);

            assertThat(updatedBlock.getId()).isEqualTo(savedBlock.getId());
            assertThat(updatedBlock.getType()).isEqualTo(modifiedType);
            assertThat(updatedBlock.getOrder()).isEqualTo(modifiedOrder);
            assertThat(updatedBlock.getDocumentId()).isEqualTo(modifiedDocumentId);
        }
    }

    @Nested
    class findByDocumentId_메서드는 {

        @Test
        void 문서_아이디로_블록을_조회한다() {
            BlockType type = BlockType.TEXT;
            Long order = 1L;
            UUID documentId = UUID.randomUUID();
            UUID memberId = UUID.randomUUID();

            Block block = Block.builder()
                    .id(null)
                    .type(type)
                    .order(order)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();

            blockRepository.save(block);

            BlockType anotherType = BlockType.IMAGE;
            Long anotherOrder = 2L;

            Block anotherBlock = Block.builder()
                    .id(null)
                    .type(anotherType)
                    .order(anotherOrder)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();

            blockRepository.save(anotherBlock);

            assertThat(blockRepository.findByDocumentId(documentId)).hasSize(2);
        }

        @Test
        void 블록의_순서로_정렬하여_반환한다() {
            BlockType type = BlockType.TEXT;
            Long order = 1L;
            UUID documentId = UUID.randomUUID();
            UUID memberId = UUID.randomUUID();

            Block block = Block.builder()
                    .id(null)
                    .type(type)
                    .order(order)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();

            blockRepository.save(block);

            BlockType anotherType = BlockType.IMAGE;
            Long anotherOrder = 2L;

            Block anotherBlock = Block.builder()
                    .id(null)
                    .type(anotherType)
                    .order(anotherOrder)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();

            blockRepository.save(anotherBlock);

            List<Block> blocks = blockRepository.findByDocumentId(documentId);
            assertThat(blocks.get(0).getOrder()).isEqualTo(1L);
            assertThat(blocks.get(1).getOrder()).isEqualTo(2L);
        }
    }

    @Nested
    class existsByDocumentIdAndOrderBetween_메서드는 {

        @Test
        void 문서_아이디와_순서_범위로_블록이_존재하는지_확인한다() {
            BlockType type = BlockType.TEXT;
            Long order = 1L;
            UUID documentId = UUID.randomUUID();
            UUID memberId = UUID.randomUUID();

            Block block = Block.builder()
                    .id(null)
                    .type(type)
                    .order(order)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();

            blockRepository.save(block);

            assertThat(blockRepository.existsByDocumentIdAndOrderBetween(documentId, 1L, 1L)).isTrue();
            assertThat(blockRepository.existsByDocumentIdAndOrderBetween(documentId, 2L, 2L)).isFalse();
        }
    }
}