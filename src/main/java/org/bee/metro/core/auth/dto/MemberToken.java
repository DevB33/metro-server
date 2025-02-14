package org.bee.metro.core.auth.dto;

import jakarta.servlet.http.Cookie;
import org.bee.metro.core.auth.api.AuthApi;

public record MemberToken(
        String accessToken,
        String refreshToken
) {

    public static Cookie generateRefreshTokenCookie(MemberToken memberToken) {
        Cookie refreshTokenCookie = new Cookie(AuthApi.REFRESH_TOKEN, memberToken.refreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7);
        refreshTokenCookie.setPath("/");
        return refreshTokenCookie;
    }

    public static Cookie generateAccessTokenCookie(MemberToken memberToken) {
        Cookie accessTokenCookie = new Cookie(AuthApi.ACCESS_TOKEN, memberToken.accessToken());
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setMaxAge(60 * 60 * 2);
        accessTokenCookie.setPath("/");
        return accessTokenCookie;
    }
}
