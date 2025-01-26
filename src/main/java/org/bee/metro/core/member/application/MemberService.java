package org.bee.metro.core.member.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.auth.dto.MemberCreationPayload;
import org.bee.metro.core.member.domain.Member;
import org.bee.metro.core.member.domain.MemberRepository;
import org.bee.metro.core.member.exception.MemberErrorCode;
import org.bee.metro.global.exception.type.NotFoundException;
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

    public Member findMemberById(UUID id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다.", MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public Member findMemberByOAuthId(String oauthId) {
        return memberRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다.", MemberErrorCode.MEMBER_NOT_FOUND));
    }

}
