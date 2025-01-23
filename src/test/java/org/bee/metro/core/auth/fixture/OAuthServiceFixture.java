package org.bee.metro.core.auth.fixture;

import org.bee.metro.core.auth.application.OAuthService;
import org.bee.metro.core.auth.common.OAuthProvider;
import org.bee.metro.core.auth.dto.MemberCreationPayload;
import org.springframework.stereotype.Component;

@Component
public class OAuthServiceFixture implements OAuthService {

    @Override
    public String getAccessToken(String authorizationCode, String state) {
        return "accessToken";
    }

    @Override
    public MemberCreationPayload getMemberInfo(String accessToken) {
        return new MemberCreationPayload(
                "oauthId",
                "name",
                "email@test.com",
                "avatar"
        );
    }

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.TEST;
    }
}
