package org.bee.metro.global.config;

import java.util.List;
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
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${client.url}")
    private String allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AccessTokenProvider jwtProvider)
            throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors
                        .configurationSource(request -> {
                            var corsConfig = new CorsConfiguration();
                            corsConfig.setAllowedOrigins(List.of(allowedOrigins));
                            corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                            corsConfig.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                            corsConfig.setAllowCredentials(true);
                            corsConfig.setExposedHeaders(List.of("Custom-Header"));
                            corsConfig.setMaxAge(3600L);
                            return corsConfig;
                        })
                )
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
