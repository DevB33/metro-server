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

    private final String name;

    @Enumerated(EnumType.STRING)
    private final LineColor color;

    public Tag(String name, LineColor color) {
        validateName(name);
        validateColor(color);

        this.name = name;
        this.color = color;
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BadRequestException("태그 값은 null이거나 비어있을 수 없습니다.", DocumentErrorCode.ARGUMENT_IS_NULL);
        }
    }

    private void validateColor(LineColor color) {
        if (color == null) {
            throw new BadRequestException("태그 색상은 null일 수 없습니다.", DocumentErrorCode.ARGUMENT_IS_NULL);
        }
    }
}
