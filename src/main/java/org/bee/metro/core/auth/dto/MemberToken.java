package org.bee.metro.core.auth.dto;

public record MemberToken(
        String accessToken,
        String refreshToken
) {
}
