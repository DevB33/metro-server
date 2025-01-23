package org.bee.metro.core.member.infra;

import java.util.Optional;
import java.util.UUID;
import org.bee.metro.core.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, UUID> {
    Optional<MemberEntity> findByOauthId(String oAuthId);
}
