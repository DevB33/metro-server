package org.bee.metro.core.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import java.util.stream.Stream;
import org.bee.metro.core.member.entity.MemberEntity;
import org.bee.metro.global.exception.type.BadRequestException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MemberTest {

    @Nested
    class Member는 {

        @Test
        void 올바른_인자가_들어오면_객체가_생성된다() {
            // given
            String name = "name";
            String email = "email@email.com";
            String avatar = "avatar";
            String oauthId = "oauthId";

            // when
            Member member = Member.builder()
                    .name(name)
                    .email(email)
                    .avatar(avatar)
                    .oauthId(oauthId)
                    .build();

            // then
            assertThat(member.getName()).isEqualTo(name);
            assertThat(member.getEmail()).isEqualTo(email);
            assertThat(member.getAvatar()).isEqualTo(avatar);
            assertThat(member.getOauthId()).isEqualTo(oauthId);
        }

        @ParameterizedTest
        @MethodSource("generateInvalidArguments")
        void 올바르지_않은_인자가_들어오면_예외가_발생한다(String name, String email) {
            // given
            String avatar = "avatar";
            String oauthId = "oauthId";

            // expect
            assertThrows(BadRequestException.class, () -> Member.builder()
                    .name(name)
                    .email(email)
                    .avatar(avatar)
                    .oauthId(oauthId)
                    .build());
        }

        private static Stream<Arguments> generateInvalidArguments() {
            return Stream.of(
                    Arguments.of(null, "email@email.com"),
                    Arguments.of("", "email@email.com"),
                    Arguments.of("name", null),
                    Arguments.of("name", ""),
                    Arguments.of("name", "email")
            );
        }
    }

    @Nested
    class fromEntity_메서드는 {

        @Test
        void MemberEntity를_받아_Member로_변환한다() {
            // given
            MemberEntity entity = MemberEntity.builder()
                    .id(UUID.randomUUID())
                    .name("name")
                    .email("email@email.com")
                    .avatar("avatar")
                    .oauthId("oauthId")
                    .build();

            // when
            Member member = Member.fromEntity(entity);

            // then
            assertThat(member.getId()).isEqualTo(entity.getId());
            assertThat(member.getName()).isEqualTo(entity.getName());
            assertThat(member.getEmail()).isEqualTo(entity.getEmail());
            assertThat(member.getAvatar()).isEqualTo(entity.getAvatar());
            assertThat(member.getOauthId()).isEqualTo(entity.getOauthId());
        }
    }
}