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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(
            @RequestBody MemberLoginRequest memberLoginRequest,
            HttpServletResponse response
    ) {
        MemberToken memberToken = authService.login(
                memberLoginRequest.authorizationCode(),
                memberLoginRequest.state(),
                OAuthProvider.valueOf(memberLoginRequest.provider())
        );

        Cookie refreshTokenCookie = MemberToken.generateRefreshTokenCookie(memberToken);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(new AccessTokenResponse(memberToken.accessToken()));
    }
}
