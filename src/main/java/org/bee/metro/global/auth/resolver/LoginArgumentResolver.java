package org.bee.metro.global.auth.resolver;

import java.util.UUID;
import org.bee.metro.global.auth.annotation.Login;
import org.bee.metro.core.auth.exception.AuthErrorCode;
import org.bee.metro.global.auth.exception.type.AuthenticationException;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasUUIDParameterType = parameter.getParameterType().isAssignableFrom(UUID.class);
        return hasLoginAnnotation && hasUUIDParameterType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        checkAuthenticated(authentication);
        return authentication.getPrincipal();
    }

    private void checkAuthenticated(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("권한이 없습니다.", AuthErrorCode.UNAUTHORIZED);
        }
    }
}
