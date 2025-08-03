package org.bee.metro.core.document.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.bee.metro.core.document.common.DocumentFieldType;
import org.bee.metro.core.document.entity.DocumentEntity;
import org.bee.metro.core.document.exception.DocumentErrorCode;
import org.bee.metro.global.exception.type.BadRequestException;

@Getter
public class Document {

    private final UUID id;
    private final String title;
    private final List<Tag> tags;
    private final String icon;
    private final String cover;
    private final UUID parentId;
    private final UUID ownerId;
    private final Long order;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static final UUID ROOT_DOCUMENT_PARENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final String ERROR_OWNER_ID_IS_NULL = "소유자 아이디는 null일 수 없습니다.";

    @Builder
    public Document(UUID id, String title, List<Tag> tag, String icon, String cover, UUID parentId, UUID ownerId,
                    Long order, LocalDateTime createdAt, LocalDateTime updatedAt) {
        validateOwnerId(ownerId);

        this.id = id;
        this.title = title;
        this.tags = tag;
        this.icon = icon;
        this.cover = cover;
        this.parentId = makeValidParentId(parentId);
        this.ownerId = ownerId;
        this.order = order;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Document fromEntity(DocumentEntity documentEntity) {
        return Document.builder()
            .id(documentEntity.getId())
            .title(documentEntity.getTitle())
            .tag(documentEntity.getTags())
            .icon(documentEntity.getIcon())
            .cover(documentEntity.getCover())
            .parentId(documentEntity.getParentId())
            .ownerId(documentEntity.getOwnerId())
            .order(documentEntity.getOrder())
            .createdAt(documentEntity.getCreatedAt())
            .updatedAt(documentEntity.getUpdatedAt())
            .build();
    }

    private void validateOwnerId(UUID ownerId) {
        if (ownerId == null) {
            throw new BadRequestException(ERROR_OWNER_ID_IS_NULL, DocumentErrorCode.ARGUMENT_IS_NULL);
        }
    }

    private UUID makeValidParentId(UUID parentId) {
        if (parentId == null) {
            return ROOT_DOCUMENT_PARENT_ID;
        }
        return parentId;
    }

    public boolean isNotOwner(UUID ownerId) {
        return !this.ownerId.equals(ownerId);
    }

    public Document updateField(DocumentFieldType documentFieldType, String value) {
        return Document.builder()
                .id(this.id)
                .title(documentFieldType == DocumentFieldType.TITLE ? value : this.title)
                .tag(this.tags)
                .icon(documentFieldType == DocumentFieldType.ICON ? value : this.icon)
                .cover(documentFieldType == DocumentFieldType.COVER ? value : this.cover)
                .parentId(this.parentId)
                .ownerId(this.ownerId)
                .order(documentFieldType == DocumentFieldType.ORDER ? Long.parseLong(value) : this.order)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Document updateTagField(List<Tag> tags) {
        return Document.builder()
            .id(this.id)
            .title(this.title)
            .tag(tags)
            .icon(this.icon)
            .cover(this.cover)
            .parentId(this.parentId)
            .ownerId(this.ownerId)
            .order(this.order)
            .createdAt(this.createdAt)
            .updatedAt(LocalDateTime.now())
            .build();
    }
}
