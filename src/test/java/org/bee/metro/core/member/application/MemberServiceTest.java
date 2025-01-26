package org.bee.metro.core.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.bee.metro.context.ServiceTest;
import org.bee.metro.core.auth.dto.MemberCreationPayload;
import org.bee.metro.core.member.domain.Member;
import org.bee.metro.global.exception.type.NotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MemberServiceTest extends ServiceTest {

    @Nested
    class createMember_메서드는 {

        @Test
        void 정상적인_요청이면_사용자를_저장한다() {
            // given
            String name = "test_name";
            String email = "test@email.test";
            String avatar = "test_avatar";
            String oauthId = "test_oauth_id";

            // when
            Member member = memberService.createMember(new MemberCreationPayload(oauthId, name, email, avatar));

            // then
            assertThat(member.getName()).isEqualTo(name);
            assertThat(member.getEmail()).isEqualTo(email);
            assertThat(member.getAvatar()).isEqualTo(avatar);
        }
    }

    @Nested
    class findByOauthId_메서드는 {

        @Test
        void 정상적인_요청이면_사용자를_조회한다() {
            // given
            String name = "test_name";
            String email = "test@email.test";
            String avatar = "test_avatar";
            String oauthId = "test_oauth_id";

            memberService.createMember(new MemberCreationPayload(oauthId, name, email, avatar));

            // when
            Member member = memberService.findMemberByOAuthId(oauthId);

            // then
            assertThat(member.getName()).isEqualTo(name);
        }

        @Test
        void 존재하지_않는_사용자일_경우_예외가_발생한다() {
            // given
            String oauthId = "test_oauth_id";

            // expect
            assertThatThrownBy(() -> memberService.findMemberByOAuthId(oauthId))
                    .isInstanceOf(NotFoundException.class);
        }
    }
}