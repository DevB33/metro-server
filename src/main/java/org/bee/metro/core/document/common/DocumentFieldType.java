package org.bee.metro.core.document.common;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import org.bee.metro.core.document.dto.DocumentUpdatePayload;

public enum DocumentFieldType {
    TITLE, ICON, TAG, COVER;

    private static final EnumMap<DocumentFieldType, Function<String, DocumentUpdatePayload>> FIELD_TYPE_MAP =
            new EnumMap<>(Map.of(
                    TITLE, value -> new DocumentUpdatePayload(value, null, null, null),
                    ICON, value -> new DocumentUpdatePayload(null, value, null, null),
                    TAG, value -> new DocumentUpdatePayload(null, null, value, null),
                    COVER, value -> new DocumentUpdatePayload(null, null, null, value)
            ));

    public static DocumentUpdatePayload createByType(DocumentFieldType type, String value) {
        return FIELD_TYPE_MAP.get(type).apply(value);
    }
}
