package org.bee.metro.core.member.infra;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.member.domain.Member;
import org.bee.metro.core.member.domain.MemberRepository;
import org.bee.metro.core.member.entity.MemberEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberCoreRepository implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(Member member) {
        MemberEntity memberEntity = MemberEntity.from(member);
        MemberEntity savedMemberEntity = memberJpaRepository.save(memberEntity);
        return Member.fromEntity(savedMemberEntity);
    }

    @Override
    public Member findByOauthId(String oAuthId) {
        MemberEntity memberEntity = memberJpaRepository.findByOauthId(oAuthId);
        if (memberEntity == null) {
            return null;
        }
        return Member.fromEntity(memberEntity);
    }

    @Override
    public Member findById(UUID id) {
        MemberEntity memberEntity = memberJpaRepository.findById(id).orElse(null);
        if (memberEntity == null) {
            return null;
        }
        return Member.fromEntity(memberEntity);
    }
}
