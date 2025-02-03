package org.bee.metro.document.config;

import org.bee.metro.global.auth.jwt.RefreshTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DocumentConfig {

    @Bean
    public RefreshTokenProvider refreshTokenProvider(
            @Value("${jwt.refresh.issuer}") String issuer,
            @Value("${jwt.refresh.secret}") String secret) {
        return new RefreshTokenProvider(issuer, secret);
    }
}
