package org.bee.metro.core.member.domain;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(UUID id);
    Optional<Member> findByOauthId(String oAuthId);
}
