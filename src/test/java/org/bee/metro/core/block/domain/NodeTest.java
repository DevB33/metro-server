package org.bee.metro.core.block.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;
import org.bee.metro.core.block.domain.node.Node;
import org.bee.metro.global.exception.type.BadRequestException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class NodeTest {

    @Nested
    class Node_생성 {

        @Test
        void 올바른_인자가_들어오면_객체가_생성된다() {
            // given
            UUID id = UUID.randomUUID();
            String content = "Hello, World!";
            Map<String, String> style = Map.of("font-size", "12px");
            Long order = 1L;
            UUID blockId = UUID.randomUUID();
            UUID documentId = UUID.randomUUID();

            // when
            Node node = Node.builder()
                    .id(id)
                    .content(content)
                    .style(style)
                    .order(order)
                    .blockId(blockId)
                    .documentId(documentId)
                    .build();

            // then
            assertThat(node).isNotNull();
            assertThat(node.getId()).isEqualTo(id);
            assertThat(node.getContent()).isEqualTo(content);
            assertThat(node.getStyle()).isEqualTo(style);
            assertThat(node.getOrder()).isEqualTo(order);
            assertThat(node.getBlockId()).isEqualTo(blockId);
            assertThat(node.getDocumentId()).isEqualTo(documentId);
        }

        @ParameterizedTest
        @MethodSource("generateInvalidArguments")
        void 올바르지_않은_인자가_들어오면_예외가_발생한다(Long order, UUID blockId, UUID documentId) {
            // given
            UUID id = UUID.randomUUID();
            String content = "Invalid Node";
            Map<String, String> style = Map.of("color", "red");

            // expect
            assertThrows(BadRequestException.class, () -> Node.builder()
                    .id(id)
                    .content(content)
                    .style(style)
                    .order(order)
                    .blockId(blockId)
                    .documentId(documentId)
                    .build());
        }

        private static Stream<Arguments> generateInvalidArguments() {
            return Stream.of(
                    Arguments.of(-1L, UUID.randomUUID(), UUID.randomUUID()),
                    Arguments.of(1L, null, UUID.randomUUID()),
                    Arguments.of(1L, UUID.randomUUID(), null)
            );
        }
    }
}
