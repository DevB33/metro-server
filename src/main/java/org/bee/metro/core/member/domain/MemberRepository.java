package org.bee.metro.core.member.domain;

import java.util.UUID;

public interface MemberRepository {
    Member save(Member member);
    Member findById(UUID id);
    Member findByOauthId(String oAuthId);
}
