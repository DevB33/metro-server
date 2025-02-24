package org.bee.metro.core.block.application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bee.metro.context.ServiceTest;
import org.bee.metro.core.block.domain.block.Block;
import org.bee.metro.core.block.domain.block.BlockType;
import org.bee.metro.core.block.domain.node.Node;
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
}