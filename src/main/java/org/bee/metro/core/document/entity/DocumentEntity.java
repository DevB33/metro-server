package org.bee.metro.core.document.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(nullable = true)
    private String tag;

    @Column(nullable = true)
    private String icon;

    @Column(nullable = true)
    private String cover;

    private UUID parentId;

    private UUID ownerId;

    @Builder
    public DocumentEntity(UUID id, String title, String tag, String icon, String cover, UUID parentId, UUID ownerId) {
        this.id = id;
        this.title = title;
        this.tag = tag;
        this.icon = icon;
        this.cover = cover;
        this.parentId = parentId;
        this.ownerId = ownerId;
    }
}
