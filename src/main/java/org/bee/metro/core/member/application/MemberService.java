package org.bee.metro.core.member.application;

import lombok.RequiredArgsConstructor;
import org.bee.metro.core.auth.dto.MemberCreationPayload;
import org.bee.metro.core.member.domain.Member;
import org.bee.metro.core.member.domain.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(MemberCreationPayload memberCreationPayload) {
        Member member = Member.builder()
                .name(memberCreationPayload.name())
                .email(memberCreationPayload.email())
                .avatar(memberCreationPayload.avatar())
                .oauthId(memberCreationPayload.oauthId())
                .build();

        return memberRepository.save(member);
    }

    public Member findMemberByOAuthId(String oAuthId) {
        return memberRepository.findByOAuthId(oAuthId);
    }
}
