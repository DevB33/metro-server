package org.bee.metro.global.auth.jwt;

public class AccessTokenProvider extends JwtProvider {

    public AccessTokenProvider(String issuer, String secret) {
        super(issuer, secret);
    }

    @Override
    long getExpirationSeconds() {
        return 60 * 60 * 2;
    }

}
