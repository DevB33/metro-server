package org.bee.metro.core.document.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;
import org.bee.metro.global.exception.type.BadRequestException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DocumentTest {

    @Nested
    class Document는 {

        @Test
        void 올바른_인자가_들어오면_객체가_생성된다() {
            // given
            String title = "title";
            String tag = "tag";
            String icon = "icon";
            String cover = "cover";
            UUID parentId = UUID.randomUUID();
            UUID ownerId = UUID.randomUUID();
            LocalDateTime createdAt = LocalDateTime.now();
            LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);

            // when
            Document document = Document.builder()
                    .title(title)
                    .tag(tag)
                    .icon(icon)
                    .cover(cover)
                    .parentId(parentId)
                    .ownerId(ownerId)
                    .createdAt(createdAt)
                    .updatedAt(updatedAt)
                    .build();

            // then
            assertThat(document.getTitle()).isEqualTo(title);
            assertThat(document.getTag()).isEqualTo(tag);
            assertThat(document.getIcon()).isEqualTo(icon);
            assertThat(document.getCover()).isEqualTo(cover);
            assertThat(document.getParentId()).isEqualTo(parentId);
            assertThat(document.getOwnerId()).isEqualTo(ownerId);
            assertThat(document.getCreatedAt()).isEqualTo(createdAt);
            assertThat(document.getUpdatedAt()).isEqualTo(updatedAt);
        }

        @ParameterizedTest
        @MethodSource("generateInvalidArguments")
        void 올바르지_않은_인자가_들어오면_예외가_발생한다(UUID parentId, UUID ownerId) {
            // given
            String title = "title";
            String tag = "tag";
            String icon = "icon";
            String cover = "cover";
            LocalDateTime createdAt = LocalDateTime.now();
            LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);

            // expect
            assertThrows(BadRequestException.class, () -> Document.builder()
                    .title(title)
                    .tag(tag)
                    .icon(icon)
                    .cover(cover)
                    .parentId(parentId)
                    .ownerId(ownerId)
                    .createdAt(createdAt)
                    .updatedAt(updatedAt)
                    .build());
        }

        private static Stream<Arguments> generateInvalidArguments() {
            return Stream.of(
                    Arguments.of(null, UUID.randomUUID()),
                    Arguments.of(UUID.randomUUID(), null)
            );
        }
    }
}