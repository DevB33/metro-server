package org.bee.metro.global.auth.jwt;

import io.jsonwebtoken.JwtParser;
import javax.crypto.SecretKey;

public class RefreshTokenProvider extends JwtProvider {

    public RefreshTokenProvider(String issuer, SecretKey secretKey, JwtParser jwtParser) {
        super(issuer, secretKey, jwtParser);
    }

    @Override
    long getExpirationSeconds() {
        return 60 * 60 * 24 * 7;
    }

}
