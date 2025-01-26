package org.bee.metro.core.auth.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.auth.common.OAuthProvider;
import org.bee.metro.core.auth.common.OAuthServiceFactory;
import org.bee.metro.core.auth.dto.MemberCreationPayload;
import org.bee.metro.core.auth.dto.MemberToken;
import org.bee.metro.core.member.application.MemberService;
import org.bee.metro.core.member.domain.Member;
import org.bee.metro.global.auth.jwt.AccessTokenProvider;
import org.bee.metro.global.auth.jwt.RefreshTokenProvider;
import org.bee.metro.global.exception.type.NotFoundException;
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
        try {
            return memberService.findMemberByOAuthId(memberPayload.oauthId());
        } catch (NotFoundException e) {
            return memberService.createMember(memberPayload);
        }
    }

    private MemberToken generateMemberToken(Member member) {
        String accessToken = accessTokenProvider.generateToken(member.getId());
        String refreshToken = refreshTokenProvider.generateToken(member.getId());
        return new MemberToken(accessToken, refreshToken);
    }

    public MemberToken refresh(UUID id) {
        Member member = memberService.findMemberById(id);
        return generateMemberToken(member);
    }

    public MemberToken testLogin() {
        MemberCreationPayload memberPayload = new MemberCreationPayload(
                "test", "test", "test@test.com", "test"
        );

        Member member = createMemberIfNotExists(memberPayload);
        return generateMemberToken(member);
    }
}
