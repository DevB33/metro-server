package org.bee.metro.core.block.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBlockEntity is a Querydsl query type for BlockEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBlockEntity extends EntityPathBase<BlockEntity> {

    private static final long serialVersionUID = 263478538L;

    public static final QBlockEntity blockEntity = new QBlockEntity("blockEntity");

    public final ComparablePath<java.util.UUID> documentId = createComparable("documentId", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final NumberPath<Long> order = createNumber("order", Long.class);

    public final EnumPath<org.bee.metro.core.block.domain.BlockType> type = createEnum("type", org.bee.metro.core.block.domain.BlockType.class);

    public QBlockEntity(String variable) {
        super(BlockEntity.class, forVariable(variable));
    }

    public QBlockEntity(Path<? extends BlockEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBlockEntity(PathMetadata metadata) {
        super(BlockEntity.class, metadata);
    }

}

