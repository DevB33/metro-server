package org.bee.metro.global.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.auth.exception.AuthErrorCode;
import org.bee.metro.global.auth.exception.type.AuthorizationException;
import org.bee.metro.global.auth.jwt.JwtProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class RefreshionFilter extends OncePerRequestFilter {

    private static final String REFRESH_URI = "/auth/refresh";
    private static final String REFRESH_TOKEN = "refreshToken";

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Cookie refreshTokenCookie = getRefreshTokenCookie(request);

        String refreshToken = refreshTokenCookie.getValue();
        JwtAuthentication jwtAuthentication = parseRefreshToken(refreshToken);

        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        jwtAuthentication.getPrincipal(),
                        refreshToken,
                        null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private JwtAuthentication parseRefreshToken(String refreshToken) {
        UUID id = jwtProvider.parseToken(refreshToken);
        return new JwtAuthentication(id, refreshToken);
    }

    private static Cookie getRefreshTokenCookie(HttpServletRequest request) {
        Cookie refreshTokenCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(REFRESH_TOKEN))
                .findFirst()
                .orElseThrow(() -> new AuthorizationException("리프레시 토큰이 없습니다.", AuthErrorCode.NOT_FOUND_TOKEN));
        return refreshTokenCookie;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI() == REFRESH_URI;
    }
}
