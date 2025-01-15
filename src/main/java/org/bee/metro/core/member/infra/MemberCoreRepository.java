package org.bee.metro.core.member.infra;

import org.bee.metro.core.member.domain.Member;
import org.bee.metro.core.member.domain.MemberRepository;
import org.bee.metro.core.member.entity.MemberEntity;
import org.springframework.stereotype.Repository;

@Repository
public class MemberCoreRepository implements MemberRepository {

    MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(Member member) {
        MemberEntity memberEntity = MemberEntity.from(member.getId(), member.getName(), member.getEmail(),
                member.getAvatar());
        MemberEntity savedMemberEntity = memberJpaRepository.save(memberEntity);
        return Member.fromEntity(savedMemberEntity);
    }
}
