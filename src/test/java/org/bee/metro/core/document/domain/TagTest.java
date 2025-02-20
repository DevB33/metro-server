package org.bee.metro.core.document.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.bee.metro.context.ServiceTest;
import org.bee.metro.global.exception.type.BadRequestException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TagTest extends ServiceTest {

    @Nested
    class 태그_생성 {

        @Test
        void 올바른_입력에_대해_태그_객체를_생성한다() {
            String value = "tagValue";
            LineColor color = LineColor.LINE_ONE;

            Tag tag = new Tag(value, color);

            assertThat(tag).isNotNull();
            assertThat(tag.getValue()).isEqualTo(value);
            assertThat(tag.getColor()).isEqualTo(color);
        }

        @ParameterizedTest
        @CsvSource({
                "null, LINE_ONE",
                "''  , LINE_ONE",
                "tagValue, null"
        })
        void 잘못된_입력에_대해_예외를_던진다(String value, LineColor color) {
            assertThrows(BadRequestException.class, () -> new Tag(value, color));
        }
    }
}