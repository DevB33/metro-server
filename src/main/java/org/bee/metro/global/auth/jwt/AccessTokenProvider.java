package org.bee.metro.global.auth.jwt;

import io.jsonwebtoken.JwtParser;
import javax.crypto.SecretKey;

public class AccessTokenProvider extends JwtProvider {

    public AccessTokenProvider(String issuer, SecretKey secretKey, JwtParser jwtParser) {
        super(issuer, secretKey, jwtParser);
    }

    @Override
    long getExpirationSeconds() {
        return 60 * 60 * 2;
    }

}
