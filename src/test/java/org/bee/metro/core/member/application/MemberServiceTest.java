package org.bee.metro.core.member.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.bee.metro.context.ServiceTest;
import org.bee.metro.core.member.domain.Member;
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

            // when
            Member member = memberService.createMember(name, email, avatar);

            // then
            assertThat(member.getName()).isEqualTo(name);
            assertThat(member.getEmail()).isEqualTo(email);
            assertThat(member.getAvatar()).isEqualTo(avatar);
        }
    }
}