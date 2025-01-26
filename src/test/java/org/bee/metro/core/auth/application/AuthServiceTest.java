package org.bee.metro.core.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.bee.metro.context.ServiceTest;
import org.bee.metro.core.auth.common.OAuthProvider;
import org.bee.metro.core.auth.dto.MemberToken;
import org.bee.metro.core.member.domain.Member;
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
            assertThat(memberToken.accessToken()).isNotNull();
            assertThat(memberToken.refreshToken()).isNotNull();
        }
    }

    @Nested
    class refresh_메서드는 {

        @Test
        void 회원_식별자를_받아_토큰을_반환한다() {
            // given
            Member member = new Member(
                    null, "test", "test@test.com", "test", "test");

            Member savedMember = memberRepository.save(member);

            // when
            MemberToken memberToken = authService.refresh(savedMember.getId());

            // then
            assertThat(memberToken.accessToken()).isNotNull();
            assertThat(memberToken.refreshToken()).isNotNull();
        }
    }
}