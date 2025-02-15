package org.bee.metro.context;

import org.bee.metro.core.document.infra.DocumentCoreRepository;
import org.bee.metro.core.document.infra.DocumentJpaRepository;
import org.bee.metro.core.document.infra.DocumentQueryDslRepository;
import org.bee.metro.core.member.infra.MemberCoreRepository;
import org.bee.metro.core.member.infra.MemberJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import({MemberCoreRepository.class, DocumentCoreRepository.class})
@DataJpaTest
public abstract class RepositoryTest {

    @Autowired
    protected MemberJpaRepository memberJpaRepository;

    @Autowired
    protected DocumentJpaRepository documentJpaRepository;

    @Autowired
    protected DocumentQueryDslRepository documentQueryDslRepository;
}
