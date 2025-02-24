package org.bee.metro.core.block.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNodeEntity is a Querydsl query type for NodeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNodeEntity extends EntityPathBase<NodeEntity> {

    private static final long serialVersionUID = -1279287541L;

    public static final QNodeEntity nodeEntity = new QNodeEntity("nodeEntity");

    public final ComparablePath<java.util.UUID> blockId = createComparable("blockId", java.util.UUID.class);

    public final StringPath content = createString("content");

    public final ComparablePath<java.util.UUID> documentId = createComparable("documentId", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final NumberPath<Long> order = createNumber("order", Long.class);

    public final StringPath style = createString("style");

    public QNodeEntity(String variable) {
        super(NodeEntity.class, forVariable(variable));
    }

    public QNodeEntity(Path<? extends NodeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNodeEntity(PathMetadata metadata) {
        super(NodeEntity.class, metadata);
    }

}

