package org.bee.metro.core.auth.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.auth.application.AuthService;
import org.bee.metro.core.auth.common.OAuthProvider;
import org.bee.metro.core.auth.dto.MemberLoginRequest;
import org.bee.metro.core.auth.dto.MemberToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String ACCESS_TOKEN = "accessToken";

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody MemberLoginRequest memberLoginRequest,
            HttpServletResponse response
    ) {
        MemberToken memberToken = authService.login(
                memberLoginRequest.authorizationCode(),
                memberLoginRequest.state(),
                OAuthProvider.valueOf(memberLoginRequest.provider().toUpperCase())
        );

        appendTokenToResponse(response, memberToken);
        return ResponseEntity.ok().build();
    }

    private static void appendTokenToResponse(HttpServletResponse response, MemberToken memberToken) {
        Cookie refreshTokenCookie = MemberToken.generateRefreshTokenCookie(memberToken);
        response.addCookie(refreshTokenCookie);

        Cookie accessTokenCookie = MemberToken.generateAccessTokenCookie(memberToken);
        response.addCookie(accessTokenCookie);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        deleteTokenCookie(REFRESH_TOKEN, response);
        deleteTokenCookie(ACCESS_TOKEN, response);
        return ResponseEntity.ok().build();
    }

    private static void deleteTokenCookie(String tokenSignature, HttpServletResponse response) {
        Cookie deletedCookie = new Cookie(tokenSignature, null);
        deletedCookie.setMaxAge(0);
        response.addCookie(deletedCookie);
    }
}
