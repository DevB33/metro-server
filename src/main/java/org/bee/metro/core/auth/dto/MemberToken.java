package org.bee.metro.core.auth.dto;

import jakarta.servlet.http.Cookie;

public record MemberToken(
        String accessToken,
        String refreshToken
) {

    public static Cookie generateRefreshTokenCookie(MemberToken memberToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", memberToken.refreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7);
        refreshTokenCookie.setPath("/");
        return refreshTokenCookie;
    }
}
