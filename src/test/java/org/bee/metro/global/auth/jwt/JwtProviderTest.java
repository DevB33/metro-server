package org.bee.metro.global.auth.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;
import org.bee.metro.global.auth.jwt.fake.FakeJwtProvider;
import org.bee.metro.global.exception.type.BadRequestException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JwtProviderTest {

    String issuer = "test";
    String secret = String.valueOf(UUID.randomUUID());

    private final JwtProvider jwtProvider = new FakeJwtProvider(issuer, secret);

    @Nested
    class generateToken_메서드는 {

        @Test
        void 식별자를_받아_JWT_토큰을_생성한다() {
            // given
            UUID subject = UUID.randomUUID();

            // when
            String token = jwtProvider.generateToken(subject);

            // then
            assertNotNull(token);
        }
    }

    @Nested
    class parseToken_메서드는 {

        @Test
        void JWT_토큰을_받아_식별자를_추출한다() {
            // given
            UUID subject = UUID.randomUUID();
            ((FakeJwtProvider) jwtProvider).setExpirationSeconds(3600L);
            String token = jwtProvider.generateToken(subject);

            // when
            UUID parsedSubject = jwtProvider.parseToken(token);

            // then
            assertThat(subject).isEqualTo(parsedSubject);
        }

        @Test
        void 만료된_JWT_토큰을_받으면_예외를_던진다() {
            // given
            UUID subject = UUID.randomUUID();
            ((FakeJwtProvider) jwtProvider).setExpirationSeconds(0L);
            String token = jwtProvider.generateToken(subject);

            // expect
            assertThatThrownBy(() -> jwtProvider.parseToken(token))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessageContaining("토큰이 만료되었습니다.");
        }
    }
}