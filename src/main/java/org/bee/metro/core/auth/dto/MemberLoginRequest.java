package org.bee.metro.core.auth.dto;

public record MemberLoginRequest(
        String authorizationCode,
        String state,
        String provider
) {
}
