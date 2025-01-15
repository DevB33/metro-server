package org.bee.metro.core.member.application;

import lombok.RequiredArgsConstructor;
import org.bee.metro.core.member.domain.Member;
import org.bee.metro.core.member.domain.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(String name, String email, String avatar) {
        Member member = Member.builder()
                .name(name)
                .email(email)
                .avatar(avatar)
                .build();

        return memberRepository.save(member);
    }
}
