package org.bee.metro.core.auth.dto;

public record MemberCreationPayload(
        String oauthId,
        String name,
        String email,
        String avatar
) {
}
