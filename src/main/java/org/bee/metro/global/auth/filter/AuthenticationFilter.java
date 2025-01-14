package org.bee.metro.global.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bee.metro.global.auth.exception.AuthErrorCode;
import org.bee.metro.global.auth.exception.type.AuthorizationException;
import org.bee.metro.global.auth.jwt.JwtProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final JwtProvider jwtProvider;

    private static final String ERROR_TOKEN_DOES_NOT_EXIST = "토큰이 없습니다. 현재 값: %s";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String bearerAccessToken = request.getHeader(AUTHORIZATION);
        if (bearerAccessToken != null && bearerAccessToken.startsWith(BEARER)) {
            String accessToken = removeBearer(bearerAccessToken);
            JwtAuthentication jwtAuthentication = parseAccessToken(accessToken);

            UsernamePasswordAuthenticationToken authentication =
                    UsernamePasswordAuthenticationToken.authenticated(
                            jwtAuthentication.getPrincipal(),
                            accessToken,
                            null);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } else {
            throw new AuthorizationException(ERROR_TOKEN_DOES_NOT_EXIST.formatted(bearerAccessToken),
                    AuthErrorCode.NOT_FOUND_TOKEN);
        }
        filterChain.doFilter(request, response);
    }

    private String removeBearer(String bearerAccessToken) {
        return bearerAccessToken.substring(BEARER.length());
    }

    private JwtAuthentication parseAccessToken(String accessToken) {
        UUID id = jwtProvider.parseToken(accessToken);
        return new JwtAuthentication(id, accessToken);
    }
}
