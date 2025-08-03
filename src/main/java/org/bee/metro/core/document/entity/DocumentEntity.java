package org.bee.metro.core.document.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bee.metro.core.document.domain.Document;
import org.bee.metro.core.document.domain.Tag;
import org.bee.metro.global.entity.BaseEntity;

@Entity
@Getter
@Table(name = "DOCUMENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = true)
    private String title;

    @Convert(converter = TagListConverter.class)
    @Column(nullable = true)
    private List<Tag> tags;

    @Column(nullable = true)
    private String icon;

    @Column(nullable = true)
    private String cover;

    @Column(nullable = true)
    private UUID parentId;

    @Column(nullable = true)
    private UUID ownerId;

    @Column(name="document_order", nullable = true)
    private Long order;

    @Builder
    public DocumentEntity(UUID id, String title, List<Tag> tags, String icon, String cover, UUID parentId,
                          UUID ownerId, UUID blockId, Long order) {
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.icon = icon;
        this.cover = cover;
        this.parentId = parentId;
        this.ownerId = ownerId;
        this.order = order;
    }

    public static DocumentEntity from(Document document) {
        List<Tag> tags = new ArrayList<>(
                Optional.ofNullable(document.getTags()).orElseGet(ArrayList::new));

        return DocumentEntity.builder()
                .id(document.getId())
                .title(document.getTitle())
                .tags(tags)
                .icon(document.getIcon())
                .cover(document.getCover())
                .parentId(document.getParentId())
                .ownerId(document.getOwnerId())
                .order(document.getOrder())
                .build();
    }
}
