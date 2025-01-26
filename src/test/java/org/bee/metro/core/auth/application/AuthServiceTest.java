package org.bee.metro.core.auth.application;

import static org.junit.jupiter.api.Assertions.*;

import org.bee.metro.context.ServiceTest;
import org.bee.metro.core.auth.common.OAuthProvider;
import org.bee.metro.core.auth.dto.MemberToken;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AuthServiceTest extends ServiceTest {

    @Nested
    class login_메서드는 {

        @Test
        void 정상적인_인증코드와_상태를_전달하면_토큰을_반환한다() {
            // given & when
            MemberToken memberToken = authService.login(
                    "authorization", "state", OAuthProvider.TEST
            );

            // then
            assertNotNull(memberToken.accessToken());
            assertNotNull(memberToken.refreshToken());
        }
    }
}