package org.bee.metro.core.document.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocumentEntity is a Querydsl query type for DocumentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDocumentEntity extends EntityPathBase<DocumentEntity> {

    private static final long serialVersionUID = 260720570L;

    public static final QDocumentEntity documentEntity = new QDocumentEntity("documentEntity");

    public final org.bee.metro.global.entity.QBaseEntity _super = new org.bee.metro.global.entity.QBaseEntity(this);

    public final StringPath cover = createString("cover");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath icon = createString("icon");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> ownerId = createComparable("ownerId", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> parentId = createComparable("parentId", java.util.UUID.class);

    public final ListPath<org.bee.metro.core.document.domain.Tag, SimplePath<org.bee.metro.core.document.domain.Tag>> tags = this.<org.bee.metro.core.document.domain.Tag, SimplePath<org.bee.metro.core.document.domain.Tag>>createList("tags", org.bee.metro.core.document.domain.Tag.class, SimplePath.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QDocumentEntity(String variable) {
        super(DocumentEntity.class, forVariable(variable));
    }

    public QDocumentEntity(Path<? extends DocumentEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDocumentEntity(PathMetadata metadata) {
        super(DocumentEntity.class, metadata);
    }

}

