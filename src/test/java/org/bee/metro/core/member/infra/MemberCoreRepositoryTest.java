package org.bee.metro.core.member.infra;

import static org.assertj.core.api.Assertions.assertThat;

import org.bee.metro.context.RepositoryTest;
import org.bee.metro.core.member.domain.Member;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberCoreRepositoryTest extends RepositoryTest {

    private final MemberCoreRepository memberCoreRepository;

    @Autowired
    MemberCoreRepositoryTest(MemberCoreRepository memberCoreRepository) {
        this.memberCoreRepository = memberCoreRepository;
    }

    @Nested
    class save_메서드는 {

        @Test
        void 회원을_저장한다() {
            // given
            Member member = Member.builder()
                    .oauthId("oAuthId")
                    .name("name")
                    .email("email@email.com")
                    .avatar("avatar")
                    .build();

            // when
            Member savedMember = memberCoreRepository.save(member);

            // then
            assertThat(savedMember).isNotNull();
        }
    }

    @Nested
    class findByOauthId_메서드는 {

        @Test
        void oAuthId로_회원을_찾는다() {
            // given
            Member member = Member.builder()
                    .oauthId("oAuthId")
                    .name("name")
                    .email("email@email.com")
                    .avatar("avatar")
                    .build();
            memberCoreRepository.save(member);

            // when
            Member foundMember = memberCoreRepository.findByOauthId("oAuthId").orElse(null);

            // then
            assertThat(foundMember).isNotNull();
        }

        @Test
        void 존재하지_않는_oauthId면_null을_반환한다() {
            // given
            Member member = Member.builder()
                    .oauthId("oAuthId")
                    .name("name")
                    .email("email@email.com")
                    .avatar("avatar")
                    .build();

            // when
            Member foundMember = memberCoreRepository.findByOauthId("oAuthId").orElse(null);

            // then
            assertThat(foundMember).isNull();
        }
    }
}