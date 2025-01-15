package org.bee.metro.core.auth.application;

import org.bee.metro.core.auth.common.OAuthProvider;
import org.bee.metro.core.auth.dto.MemberCreationPayload;

public interface OAuthService {

    default MemberCreationPayload getMemberCreationPayload(String authorizationCode, String state) {
        String accessToken = getAccessToken(authorizationCode, state);
        return getMemberInfo(accessToken);
    }

    String getAccessToken(String authorizationCode, String state);
    MemberCreationPayload getMemberInfo(String accessToken);

    OAuthProvider getProvider();
}
