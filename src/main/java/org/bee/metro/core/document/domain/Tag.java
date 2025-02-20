package org.bee.metro.core.document.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bee.metro.core.document.exception.DocumentErrorCode;
import org.bee.metro.global.exception.type.BadRequestException;

@Getter
@Embeddable
@NoArgsConstructor(force = true)
public class Tag {

    private final String value;

    @Enumerated(EnumType.STRING)
    private final LineColor color;

    public Tag(String value, LineColor color) {
        validateValue(value);
        validateColor(color);

        this.value = value;
        this.color = color;
    }

    private void validateValue(String value) {
        if (value == null || value.isEmpty()) {
            throw new BadRequestException("태그 값은 null이거나 비어있을 수 없습니다.", DocumentErrorCode.ARGUMENT_IS_NULL);
        }
    }

    private void validateColor(LineColor color) {
        if (color == null) {
            throw new BadRequestException("태그 색상은 null일 수 없습니다.", DocumentErrorCode.ARGUMENT_IS_NULL);
        }
    }
}
