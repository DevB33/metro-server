package org.bee.metro.core.auth.common;

public enum OAuthProvider {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao")
    ;

    private final String provider;

    OAuthProvider(String provider) {
        this.provider = provider;
    }
}
