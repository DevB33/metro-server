package org.bee.metro.core.block.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.block.domain.block.InnerNode;
import org.bee.metro.core.block.exception.BlockErrorCode;
import org.bee.metro.global.exception.type.BadRequestException;

@Converter
@RequiredArgsConstructor
public class NodeConverter implements AttributeConverter<List<InnerNode>, String> {

    public static final String ERROR_CONVERT_TO_STRING = "해당 노드를 문자열로 변경할 수 없습니다. 노드: %s";
    public static final String ERROR_CONVERT_TO_NODE = "해당 문자열을 노드로 변경할 수 없습니다. 문자열: %s";

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<InnerNode> node) {
        try {
            return objectMapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            throw new BadRequestException(ERROR_CONVERT_TO_STRING.formatted(node),
                    BlockErrorCode.CONVERT_TO_STRING_FAILED);
        }
    }

    @Override
    public List<InnerNode> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<InnerNode>>() {});
        } catch (JsonProcessingException e) {
            throw new BadRequestException(ERROR_CONVERT_TO_NODE.formatted(dbData),
                    BlockErrorCode.CONVERT_TO_NODES_FAILED);
        }
    }
}
