package org.bee.metro.core.block.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bee.metro.context.ServiceTest;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.block.BlockType;
import org.bee.metro.core.block.domain.node.Node;
import org.bee.metro.core.block.dto.DetailBlockPayload;
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
}