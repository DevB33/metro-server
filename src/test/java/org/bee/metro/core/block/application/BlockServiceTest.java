package org.bee.metro.core.block.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bee.metro.context.ServiceTest;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.block.BlockType;
import org.bee.metro.core.block.domain.node.Node;
import org.bee.metro.core.block.dto.DetailBlockPayload;
import org.bee.metro.global.exception.type.BadRequestException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BlockServiceTest extends ServiceTest {

    @Nested
    class createBlock_메서드는 {

        @Test
        void 인자를_받아_Block_객체를_저장한다() {
            UUID memberId = UUID.randomUUID();
            UUID documentId = UUID.randomUUID();
            BlockType type = BlockType.TEXT;
            Long order = 1L;

            Block block = blockService.createBlock(memberId, documentId, type, order);

            assertAll(
                    () -> assertNotNull(block.getId()),
                    () -> assertEquals(type, block.getType()),
                    () -> assertEquals(order, block.getOrder()),
                    () -> assertEquals(documentId, block.getDocumentId()),
                    () -> assertEquals(memberId, block.getMemberId())
            );
        }

        @Test
        void 생성_시_기본_Node를_생성한다() {
            UUID memberId = UUID.randomUUID();
            UUID documentId = UUID.randomUUID();
            BlockType type = BlockType.TEXT;
            Long order = 1L;

            Block block = blockService.createBlock(memberId, documentId, type, order);
            List<Node> nodeList = nodeRepository.findByBlockId(block.getId());

            assertEquals(1, nodeList.size());
        }
    }

    @Nested
    class createNode_메서드는 {

        @Test
        void 인자를_받아_Node_객체를_저장한다() {
            UUID blockId = UUID.randomUUID();
            UUID documentId = UUID.randomUUID();
            String content = "content";
            Long order = 1L;
            Map<String, String> style = Map.of("key", "value");
            Node node = blockService.createNode(blockId, documentId, content, order, style);

            assertAll(
                    () -> assertNotNull(node.getId()),
                    () -> assertEquals(content, node.getContent()),
                    () -> assertEquals(order, node.getOrder()),
                    () -> assertEquals(blockId, node.getBlockId()),
                    () -> assertEquals(style, node.getStyle())
            );
        }
    }

    @Nested
    class findByDocumentId_메서드는 {

        @Test
        void 문서_ID를_받아_Block_객체를_조회한다() {
            UUID documentId = UUID.randomUUID();
            Block block = blockService.createBlock(UUID.randomUUID(), documentId, BlockType.TEXT, 1L);
            Node node1 = blockService.createNode(block.getId(), documentId, "content", 1L, Map.of("key", "value"));
            Node node2 = blockService.createNode(block.getId(), documentId, "content", 2L, Map.of("key", "value"));

            List<DetailBlockPayload> detailBlockPayloadList = blockService.findByDocumentId(documentId);

            assertEquals(1, detailBlockPayloadList.size());
            DetailBlockPayload detailBlockPayload = detailBlockPayloadList.get(0);

            assertAll(
                    () -> assertThat(detailBlockPayload.block().getId()).isEqualTo(block.getId()),
                    () -> assertThat(detailBlockPayload.nodes()).hasSize(3),
                    () -> assertThat(detailBlockPayload.nodes().get(1).getId()).isEqualTo(node1.getId()),
                    () -> assertThat(detailBlockPayload.nodes().get(2).getId()).isEqualTo(node2.getId())
            );
        }
    }

    @Nested
    class updateNodeContent_메서드는 {

        @Test
        void 내용을_수정한_새로운_객체를_생성한다() {
            Node node = Node.builder()
                    .content("content")
                    .style(Map.of("key", "value"))
                    .order(1L)
                    .documentId(UUID.randomUUID())
                    .blockId(UUID.randomUUID())
                    .build();
            Node savedNode = nodeRepository.save(node);

            String updatedContent = "updatedContent";
            blockService.updateNodeContent(savedNode.getId(), UUID.randomUUID(), updatedContent);

            Node updatedNode = nodeRepository.findById(savedNode.getId()).orElseThrow();
            assertAll(
                    () -> assertThat(updatedNode.getId()).isEqualTo(savedNode.getId()),
                    () -> assertThat(updatedNode.getContent()).isEqualTo("updatedContent")
            );
        }
    }

    @Nested
    class updateNodeStyle_메서드는 {

        @Test
        void 스타일을_수정한_새로운_객체를_생성한다() {
            Node node = Node.builder()
                    .content("content")
                    .style(Map.of("key", "value"))
                    .order(1L)
                    .documentId(UUID.randomUUID())
                    .blockId(UUID.randomUUID())
                    .build();
            Node savedNode = nodeRepository.save(node);

            Map<String, String> updatedStyle = Map.of("key", "updatedValue");
            blockService.updateNodeStyle(savedNode.getId(), UUID.randomUUID(), updatedStyle);

            Node updatedNode = nodeRepository.findById(savedNode.getId()).orElseThrow();
            assertAll(
                    () -> assertThat(updatedNode.getId()).isEqualTo(savedNode.getId()),
                    () -> assertThat(updatedNode.getStyle()).isEqualTo(updatedStyle)
            );
        }
    }

    @Nested
    class updateBlocksOrder_메서드는 {

        @Test
        void 블록의_순서를_수정한다() {
            UUID documentId = UUID.randomUUID();
            UUID memberId = UUID.randomUUID();
            Block block1 = Block.builder()
                    .type(BlockType.TEXT)
                    .order(1L)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();
            Block savedBlock1 = blockRepository.save(block1);

            Block block2 = Block.builder()
                    .type(BlockType.TEXT)
                    .order(2L)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();
            Block savedBlock2 = blockRepository.save(block2);

            blockService.updateBlocksOrder(documentId, memberId, savedBlock1.getOrder(), savedBlock1.getOrder(),
                    savedBlock2.getOrder());

            List<Block> updatedBlock = blockRepository.findByDocumentId(documentId);
            assertAll(
                    () -> assertThat(updatedBlock).hasSize(2),
                    () -> assertThat(updatedBlock.get(0).getId()).isEqualTo(savedBlock2.getId()),
                    () -> assertThat(updatedBlock.get(0).getOrder()).isEqualTo(2L),
                    () -> assertThat(updatedBlock.get(1).getId()).isEqualTo(savedBlock1.getId()),
                    () -> assertThat(updatedBlock.get(1).getOrder()).isEqualTo(3L)
            );
        }

        @Test
        void 여러_개의_블록_순서를_수정한다() {
            UUID documentId = UUID.randomUUID();
            UUID memberId = UUID.randomUUID();
            Block block1 = Block.builder()
                    .type(BlockType.TEXT)
                    .order(1L)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();
            Block savedBlock1 = blockRepository.save(block1);

            Block block2 = Block.builder()
                    .type(BlockType.TEXT)
                    .order(2L)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();
            Block savedBlock2 = blockRepository.save(block2);

            Block block3 = Block.builder()
                    .type(BlockType.TEXT)
                    .order(3L)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();
            Block savedBlock3 = blockRepository.save(block3);

            blockService.updateBlocksOrder(documentId, memberId, savedBlock1.getOrder(), savedBlock1.getOrder() + 1,
                    savedBlock3.getOrder());

            List<Block> updatedBlock = blockRepository.findByDocumentId(documentId);
            assertAll(
                    () -> assertThat(updatedBlock).hasSize(3),
                    () -> assertThat(updatedBlock.get(0).getId()).isEqualTo(savedBlock3.getId()),
                    () -> assertThat(updatedBlock.get(0).getOrder()).isEqualTo(3L),
                    () -> assertThat(updatedBlock.get(1).getId()).isEqualTo(savedBlock1.getId()),
                    () -> assertThat(updatedBlock.get(1).getOrder()).isEqualTo(4L),
                    () -> assertThat(updatedBlock.get(2).getId()).isEqualTo(savedBlock2.getId()),
                    () -> assertThat(updatedBlock.get(2).getOrder()).isEqualTo(5L)
            );
        }

        @Test
        void 순서_변경이_중간이라면_중간_블록은_뒤로_보내진다() {
            UUID documentId = UUID.randomUUID();
            UUID memberId = UUID.randomUUID();
            Block block1 = Block.builder()
                    .type(BlockType.TEXT)
                    .order(1L)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();
            Block savedBlock1 = blockRepository.save(block1);

            Block block2 = Block.builder()
                    .type(BlockType.TEXT)
                    .order(2L)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();
            Block savedBlock2 = blockRepository.save(block2);

            Block block3 = Block.builder()
                    .type(BlockType.TEXT)
                    .order(3L)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();
            Block savedBlock3 = blockRepository.save(block3);

            blockService.updateBlocksOrder(documentId, memberId, savedBlock1.getOrder(), savedBlock1.getOrder(), savedBlock2.getOrder());

            List<Block> updatedBlock = blockRepository.findByDocumentId(documentId);
            assertAll(
                    () -> assertThat(updatedBlock).hasSize(3),
                    () -> assertThat(updatedBlock.get(0).getId()).isEqualTo(savedBlock2.getId()),
                    () -> assertThat(updatedBlock.get(0).getOrder()).isEqualTo(2L),
                    () -> assertThat(updatedBlock.get(1).getId()).isEqualTo(savedBlock1.getId()),
                    () -> assertThat(updatedBlock.get(1).getOrder()).isEqualTo(3L),
                    () -> assertThat(updatedBlock.get(2).getId()).isEqualTo(savedBlock3.getId()),
                    () -> assertThat(updatedBlock.get(2).getOrder()).isEqualTo(4L)
            );
        }

        @Test
        void 해당_블록들의_소유자가_아니라면_예외가_발생한다() {
            UUID documentId = UUID.randomUUID();
            UUID memberId = UUID.randomUUID();
            Block block = Block.builder()
                    .type(BlockType.TEXT)
                    .order(1L)
                    .documentId(documentId)
                    .memberId(memberId)
                    .build();
            Block savedBlock = blockRepository.save(block);

            assertThatThrownBy(
                    () -> blockService.updateBlocksOrder(documentId, UUID.randomUUID(), savedBlock.getOrder(),
                            savedBlock.getOrder(), savedBlock.getOrder()))
                    .isInstanceOf(BadRequestException.class);
        }
    }
}