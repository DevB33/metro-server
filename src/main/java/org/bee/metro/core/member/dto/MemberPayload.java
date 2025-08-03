package org.bee.metro.core.member.dto;

import org.bee.metro.core.member.domain.Member;

public record MemberPayload(
        String name,
        String email,
        String avatar
) {

    public static MemberPayload createByMember(Member member) {
        return new MemberPayload(
                member.getName(),
                member.getEmail(),
                member.getAvatar()
        );
    }
}
