package org.bee.metro.core.auth.dto;

public record MemberCreationPayload(
        String name,
        String email,
        String avatar
) {
}
