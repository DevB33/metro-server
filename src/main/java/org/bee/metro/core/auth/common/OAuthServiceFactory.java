package org.bee.metro.core.auth.common;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.bee.metro.core.auth.application.OAuthService;
import org.springframework.stereotype.Component;

@Component
public class OAuthServiceFactory {
    private final Map<OAuthProvider, OAuthService> services;

    public OAuthServiceFactory(List<OAuthService> services) {
        this.services = services.stream()
                .collect(Collectors.toMap(OAuthService::getProvider, Function.identity()));
    }

    public OAuthService getService(OAuthProvider provider) {
        return services.get(provider);
    }
}