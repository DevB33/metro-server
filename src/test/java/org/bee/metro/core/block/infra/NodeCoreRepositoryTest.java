package org.bee.metro.core.block.infra;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.UUID;
import org.bee.metro.context.RepositoryTest;
import org.bee.metro.core.block.domain.node.Node;
import org.bee.metro.core.block.domain.node.NodeRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class NodeCoreRepositoryTest extends RepositoryTest {

    private final NodeRepository nodeRepository;

    @Autowired
    NodeCoreRepositoryTest(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Nested
    class save_메서드는 {

        @Test
        void 아이디가_없다면_노드를_생성한다() {
            // given
            String content = "content";
            Long order = 1L;
            UUID blockId = UUID.randomUUID();
            UUID documentId = UUID.randomUUID();
            Map<String, String> attributes = Map.of("key", "value");

            // when
            Node node = Node.builder()
                    .id(null)
                    .content(content)
                    .style(attributes)
                    .order(order)
                    .blockId(blockId)
                    .documentId(documentId)
                    .build();

            Node savedNode = nodeRepository.save(node);

            // then
            assertThat(savedNode.getId()).isNotNull();
            assertThat(savedNode.getContent()).isEqualTo(content);
            assertThat(savedNode.getStyle()).isEqualTo(attributes);
            assertThat(savedNode.getOrder()).isEqualTo(order);
            assertThat(savedNode.getBlockId()).isEqualTo(blockId);
        }

        @Test
        void 아이디가_존재하면_노드를_수정한다() {
            // given
            String content = "content";
            Long order = 1L;
            UUID blockId = UUID.randomUUID();
            UUID documentId = UUID.randomUUID();

            Node node = Node.builder()
                    .id(null)
                    .content(content)
                    .order(order)
                    .blockId(blockId)
                    .documentId(documentId)
                    .build();

            Node savedNode = nodeRepository.save(node);

            // when
            String updatedContent = "updatedContent";
            Long updatedOrder = 2L;
            UUID updatedBlockId = UUID.randomUUID();

            Node updatedNode = Node.builder()
                    .id(savedNode.getId())
                    .content(updatedContent)
                    .order(updatedOrder)
                    .blockId(updatedBlockId)
                    .documentId(documentId)
                    .build();

            Node savedUpdatedNode = nodeRepository.save(updatedNode);

            // then
            assertThat(savedUpdatedNode.getId()).isEqualTo(savedNode.getId());
            assertThat(savedUpdatedNode.getContent()).isEqualTo(updatedContent);
            assertThat(savedUpdatedNode.getOrder()).isEqualTo(updatedOrder);
            assertThat(savedUpdatedNode.getBlockId()).isEqualTo(updatedBlockId);
        }
    }

    @Nested
    class findById_메서드는 {

        @Test
        void 존재하는_노드를_조회한다() {
            // given
            String content = "content";
            Long order = 1L;
            UUID blockId = UUID.randomUUID();
            UUID documentId = UUID.randomUUID();
            Map<String, String> attributes = Map.of("key", "value");

            Node node = Node.builder()
                    .id(null)
                    .content(content)
                    .style(attributes)
                    .order(order)
                    .blockId(blockId)
                    .documentId(documentId)
                    .build();

            Node savedNode = nodeRepository.save(node);

            // when
            Node foundNode = nodeRepository.findById(savedNode.getId()).orElse(null);

            // then
            assertThat(foundNode).isNotNull();
            assertThat(foundNode.getId()).isEqualTo(savedNode.getId());
            assertThat(foundNode.getContent()).isEqualTo(content);
            assertThat(foundNode.getStyle()).isEqualTo(attributes);
            assertThat(foundNode.getOrder()).isEqualTo(order);
            assertThat(foundNode.getBlockId()).isEqualTo(blockId);
        }

        @Test
        void 존재하지_않는_노드를_조회하면_null을_반환한다() {
            // given
            UUID id = UUID.randomUUID();

            // when
            Node foundNode = nodeRepository.findById(id).orElse(null);

            // then
            assertThat(foundNode).isNull();
        }
    }
}