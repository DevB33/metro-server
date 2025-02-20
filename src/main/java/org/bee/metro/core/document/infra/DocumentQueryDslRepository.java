package org.bee.metro.core.document.infra;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.document.common.DocumentFieldType;
import org.bee.metro.core.document.domain.Tag;
import org.bee.metro.core.document.entity.QDocumentEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentQueryDslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void updateDocument(UUID id, DocumentFieldType type, String value) {
        QDocumentEntity documentEntity = QDocumentEntity.documentEntity;
        JPAUpdateClause updated = queryFactory.update(documentEntity)
                .where(documentEntity.id.eq(id));

        switch (type) {
            case TITLE -> updated.set(documentEntity.title, value);
            case ICON -> updated.set(documentEntity.icon, value);
            case COVER -> updated.set(documentEntity.cover, value);
        }
        updated.execute();
        flushAndClear();
    }

    public void updateTags(UUID id, List<Tag> tags) {
        QDocumentEntity documentEntity = QDocumentEntity.documentEntity;
        queryFactory.update(documentEntity)
                .where(documentEntity.id.eq(id))
                .set(documentEntity.tags, tags)
                .execute();
        flushAndClear();
    }

    private void flushAndClear() {
        em.flush();
        em.clear();
    }
}
