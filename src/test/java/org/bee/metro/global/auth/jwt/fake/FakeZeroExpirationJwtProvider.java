package org.bee.metro.global.auth.jwt.fake;

import org.bee.metro.global.auth.jwt.JwtProvider;

public class FakeZeroExpirationJwtProvider extends JwtProvider {

    public FakeZeroExpirationJwtProvider(String issuer, String secret) {
        super(issuer, secret);
    }

    @Override
    protected long getExpirationSeconds() {
        return 0;
    }

}
