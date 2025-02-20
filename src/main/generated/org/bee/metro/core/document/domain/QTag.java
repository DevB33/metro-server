package org.bee.metro.core.document.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTag is a Querydsl query type for Tag
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QTag extends BeanPath<Tag> {

    private static final long serialVersionUID = 1320346271L;

    public static final QTag tag = new QTag("tag");

    public final EnumPath<LineColor> color = createEnum("color", LineColor.class);

    public final StringPath value = createString("value");

    public QTag(String variable) {
        super(Tag.class, forVariable(variable));
    }

    public QTag(Path<? extends Tag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTag(PathMetadata metadata) {
        super(Tag.class, metadata);
    }

}

