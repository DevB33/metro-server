package org.bee.metro.global.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.bee.metro.core.auth.exception.AuthErrorCode;
import org.bee.metro.global.exception.type.BadRequestException;

@Slf4j
public abstract class JwtProvider {

    private final String issuer;
    private final SecretKey secretKey;
    private final JwtParser jwtParser;

    public JwtProvider(String issuer, String secret) {
        this.issuer = issuer;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    public String generateToken(UUID subject) {
        long expireSeconds = this.getExpirationSeconds();

        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + expireSeconds * 1000L);
        return Jwts.builder()
                .issuer(issuer)
                .issuedAt(now)
                .subject(String.valueOf(subject))
                .expiration(expiresAt)
                .signWith(secretKey)
                .compact();
    }

    public UUID parseToken(String token) {
        try {
            Claims claims = jwtParser.parseSignedClaims(token).getPayload();
            return UUID.fromString(claims.getSubject());
        } catch (ExpiredJwtException e) {
            throw new BadRequestException("토큰이 만료되었습니다.", AuthErrorCode.EXPIRED_TOKEN);
        } catch (RuntimeException e) {
            log.info("유효하지 않은 토큰입니다: {}", token);
            throw new BadRequestException("유효하지 않은 토큰입니다.", AuthErrorCode.INVALID_TOKEN);
        }
    }

    abstract long getExpirationSeconds();
}
