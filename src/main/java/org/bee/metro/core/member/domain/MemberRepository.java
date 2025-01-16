package org.bee.metro.core.member.domain;

public interface MemberRepository {
    Member save(Member member);
    Member findByOAuthId(String oAuthId);
}
