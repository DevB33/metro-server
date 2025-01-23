package org.bee.metro.core.auth.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.auth.application.AuthService;
import org.bee.metro.core.auth.common.OAuthProvider;
import org.bee.metro.core.auth.dto.AccessTokenResponse;
import org.bee.metro.core.auth.dto.MemberLoginRequest;
import org.bee.metro.core.auth.dto.MemberToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

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

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam String code, @RequestParam(required = false) String state) {
        System.out.println("code = " + code);
        return ResponseEntity.ok().build();
    }
}
