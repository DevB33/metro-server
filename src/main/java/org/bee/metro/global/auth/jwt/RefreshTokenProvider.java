package org.bee.metro.global.auth.jwt;

public class RefreshTokenProvider extends JwtProvider {

    public RefreshTokenProvider(String issuer, String secret) {
        super(issuer, secret);
    }

    @Override
    protected long getExpirationSeconds() {
        return 60 * 60 * 24 * 7;
    }

}
