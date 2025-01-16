package org.bee.metro.core.auth.application;

import lombok.RequiredArgsConstructor;
import org.bee.metro.core.auth.common.OAuthProvider;
import org.bee.metro.core.auth.common.OAuthServiceFactory;
import org.bee.metro.core.auth.dto.MemberCreationPayload;
import org.bee.metro.core.auth.dto.MemberToken;
import org.bee.metro.core.member.application.MemberService;
import org.bee.metro.core.member.domain.Member;
import org.bee.metro.global.auth.jwt.AccessTokenProvider;
import org.bee.metro.global.auth.jwt.RefreshTokenProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;

    private final OAuthServiceFactory oAuthServiceFactory;
    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;

    public MemberToken login(String authorizationCode, String state, OAuthProvider provider) {
        OAuthService oAuthService = oAuthServiceFactory.getService(provider);
        MemberCreationPayload memberPayload = oAuthService.getMemberCreationPayload(authorizationCode, state);

        Member member = createMemberIfNotExists(memberPayload);
        return generateMemberToken(member);
    }

    private Member createMemberIfNotExists(MemberCreationPayload memberPayload) {
        Member member = memberService.findMemberByOAuthId(memberPayload.oauthId());
        if (member == null) {
            member = memberService.createMember(memberPayload);
        }
        return member;
    }

    private MemberToken generateMemberToken(Member member) {
        String accessToken = accessTokenProvider.generateToken(String.valueOf(member.getId()));
        String refreshToken = refreshTokenProvider.generateToken(String.valueOf(member.getId()));
        return new MemberToken(accessToken, refreshToken);
    }
}
