package org.bee.metro.global.auth.jwt.fake;

import org.bee.metro.global.auth.jwt.JwtProvider;

public class FakeJwtProvider extends JwtProvider {

    private long expirationSeconds = 3600L;

    public FakeJwtProvider(String issuer, String secret) {
        super(issuer, secret);
    }

    @Override
    protected long getExpirationSeconds() {
        return this.expirationSeconds;
    }

    public void setExpirationSeconds(long expirationSeconds) {
        this.expirationSeconds = expirationSeconds;
    }
}
