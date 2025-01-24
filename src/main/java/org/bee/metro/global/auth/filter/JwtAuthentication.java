package org.bee.metro.global.auth.filter;

import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthentication {

    private final UUID id;
    private final String token;

    public UUID getPrincipal() {
        return id;
    }

    public String getCredentials() {
        return token;
    }
}
