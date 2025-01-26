package org.bee.metro.global.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.auth.exception.AuthErrorCode;
import org.bee.metro.global.auth.exception.type.AuthenticationException;
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
    private static final List<Pattern> WHITE_LIST = List.of(
            Pattern.compile("^/auth/login$"),
            Pattern.compile("^/h2-console$"),
            Pattern.compile("^/h2-console/.*"),
            Pattern.compile("^/auth/test-login$")
    );

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
            throw new AuthenticationException(ERROR_TOKEN_DOES_NOT_EXIST.formatted(bearerAccessToken),
                    AuthErrorCode.NOT_FOUND_TOKEN);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        for (Pattern pattern : WHITE_LIST) {
            if (pattern.matcher(path).matches()) {
                return true;
            }
        }
        return false;
    }

    private String removeBearer(String bearerAccessToken) {
        return bearerAccessToken.substring(BEARER.length());
    }

    private JwtAuthentication parseAccessToken(String accessToken) {
        UUID id = jwtProvider.parseToken(accessToken);
        return new JwtAuthentication(id, accessToken);
    }
}
