package org.bee.metro.context;

import org.bee.metro.core.document.infra.DocumentCoreRepository;
import org.bee.metro.core.document.infra.DocumentJpaRepository;
import org.bee.metro.core.document.infra.DocumentQueryDslRepository;
import org.bee.metro.core.member.infra.MemberCoreRepository;
import org.bee.metro.core.member.infra.MemberJpaRepository;
import org.bee.metro.global.config.JpaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import({JpaConfig.class, MemberCoreRepository.class, DocumentCoreRepository.class, DocumentQueryDslRepository.class})
@DataJpaTest
public abstract class RepositoryTest {

    @Autowired
    protected MemberJpaRepository memberJpaRepository;

    @Autowired
    protected DocumentJpaRepository documentJpaRepository;
}
