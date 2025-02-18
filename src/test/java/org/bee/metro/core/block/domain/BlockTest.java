package org.bee.metro.core.block.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import java.util.stream.Stream;
import org.bee.metro.global.exception.type.BadRequestException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BlockTest {

    @Nested
    class Block_생성 {

        @Test
        void 올바른_인자가_들어오면_객체가_생성된다() {
            // given
            UUID id = UUID.randomUUID();
            BlockType type = BlockType.TEXT;
            Long order = 1L;
            UUID documentId = UUID.randomUUID();

            // when
            Block block = Block.builder()
                    .id(id)
                    .type(type)
                    .order(order)
                    .documentId(documentId)
                    .build();

            // then
            assertThat(block).isNotNull();
            assertThat(block.getId()).isEqualTo(id);
            assertThat(block.getType()).isEqualTo(type);
            assertThat(block.getOrder()).isEqualTo(order);
            assertThat(block.getDocumentId()).isEqualTo(documentId);
        }

        @ParameterizedTest
        @MethodSource("generateInvalidArguments")
        void 올바르지_않은_인자가_들어오면_예외가_발생한다(Long order, UUID documentId) {
            // given
            UUID id = UUID.randomUUID();
            BlockType type = BlockType.TEXT;

            // expect
            assertThrows(BadRequestException.class, () -> Block.builder()
                    .id(id)
                    .type(type)
                    .order(order)
                    .documentId(documentId)
                    .build());
        }

        private static Stream<Arguments> generateInvalidArguments() {
            return Stream.of(
                    Arguments.of(-1L, UUID.randomUUID()),
                    Arguments.of(1L, null)
            );
        }
    }
}