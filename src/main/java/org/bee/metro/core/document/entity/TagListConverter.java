package org.bee.metro.core.document.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.document.domain.Tag;
import org.bee.metro.core.document.exception.DocumentErrorCode;
import org.bee.metro.global.exception.type.BadRequestException;

@Converter
@RequiredArgsConstructor
public class TagListConverter implements AttributeConverter<List<Tag>, String> {

    public static final String ERROR_CONVERT_TO_STRING = "해당 태그 목록을 문자열로 변경할 수 없습니다. 태그 목록: %s";
    public static final String ERROR_CONVERT_TO_TAGS = "해당 문자열을 태그 목록으로 변경할 수 없습니다. 문자열: %s";

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<Tag> tags) {
        try {
            return objectMapper.writeValueAsString(tags);
        } catch (JsonProcessingException e) {
            throw new BadRequestException(ERROR_CONVERT_TO_STRING.formatted(tags),
                    DocumentErrorCode.CONVERT_TO_STRING_FAILED);
        }
    }

    @Override
    public List<Tag> convertToEntityAttribute(String attribute) {
        try {
            return objectMapper.readValue(attribute, new TypeReference<List<Tag>>() {});
        } catch (JsonProcessingException e) {
            throw new BadRequestException(ERROR_CONVERT_TO_TAGS.formatted(attribute),
                    DocumentErrorCode.CONVERT_TO_TAGS_FAILED);
        }
    }
}
