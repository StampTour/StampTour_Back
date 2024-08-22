package com.stamptour.finalstamp.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    // JWT 비밀 키를 초기화합니다.
    @PostConstruct
    public void init() {
        try {
            this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
            logger.info("JWT secret key initialized.");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid JWT secret key: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize JWT secret key", e);
        }
    }

    // JWT 토큰을 생성합니다.
    public String generateToken(String username) {
        final long tokenValidTime = 24 * 60 * 60 * 1000; // 24시간
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username) // 토큰의 주체 (Subject)
                .setIssuedAt(now) // 발급 시간 설정
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘 및 비밀 키 설정
                .compact();
    }

    // JWT 토큰에서 사용자 이름을 추출합니다.
    public String extractUsername(String token) {
        try {
            return getClaims(token).getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Token extraction failed: {}", e.getMessage());
            throw new RuntimeException("Token extraction failed", e);
        }
    }

    // JWT 토큰이 유효한지 확인합니다.
    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token has expired: {}", e.getMessage());
            throw new JwtException("JWT token has expired", e);
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtException("Invalid JWT token", e);
        }
    }

    // JWT 토큰에서 Claims를 추출합니다.
    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Failed to parse JWT claims: {}", e.getMessage());
            throw new JwtException("Invalid JWT token", e);
        }
    }
}
