package org.bee.metro.global.config;

import org.bee.metro.global.auth.filter.AuthenticationFilter;
import org.bee.metro.global.auth.filter.RefreshionFilter;
import org.bee.metro.global.auth.jwt.AccessTokenProvider;
import org.bee.metro.global.auth.jwt.RefreshTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AccessTokenProvider jwtProvider) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/login", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .headers((headerConfig) ->
                        headerConfig.frameOptions((frameOptionsConfig -> frameOptionsConfig.disable())))
                .addFilterBefore(
                        new AuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(
                        new RefreshionFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class
                );

        return httpSecurity.build();
    }

    @Bean
    public AccessTokenProvider accessTokenProvider(
            @Value("${jwt.access.issuer}") String issuer,
            @Value("${jwt.access.secret}") String secret) {
        return new AccessTokenProvider(issuer, secret);
    }

    @Bean
    public RefreshTokenProvider refreshTokenProvider(
            @Value("${jwt.refresh.issuer}") String issuer,
            @Value("${jwt.refresh.secret}") String secret) {
        return new RefreshTokenProvider(issuer, secret);
    }
}
