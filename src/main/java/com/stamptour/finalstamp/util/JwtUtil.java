package com.stamptour.finalstamp.util;

import io.jsonwebtoken.*;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String username) {
        final long tokenValidTime = 24 * 60 * 60 * 1000;
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username) // 필수: 토큰의 주체 (Subject
                .setIssuedAt(now) // 발급 시간 설정
                .setExpiration(new Date(now.getTime() + tokenValidTime))//만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

//    public void addJwtCookie(HttpServletResponse response, String jwtToken) {
//        Cookie cookie = new Cookie("access_token", jwtToken);
//        cookie.setHttpOnly(true); // 클라이언트 측 스크립트에서 접근할 수 없도록 설정합니다.
//        cookie.setSecure(true); // HTTPS 연결에서만 전송되도록 설정합니다.
//        cookie.setPath("/"); // 쿠키의 유효 범위를 설정합니다.
//        cookie.setMaxAge(60 * 60); // 쿠키의 만료 시간을 설정합니다 (여기서는 1시간).
//
//        response.addCookie(cookie);
//    }

    public String extractUsername(String token) {
        try {
            return getClaims(token).getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Token extraction failed", e);
        }
    }

    // JWT 토큰이 유효한지 확인합니다.
    public boolean isTokenValid(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에 대한 예외 처리
            throw new JwtException("JWT token has expired", e);
        } catch (JwtException | IllegalArgumentException e) {
            // 유효하지 않은 토큰에 대한 예외 처리
            throw new JwtException("Invalid JWT token", e);
        }
    }


    // JWT 토큰에서 Claims를 추출합니다.
    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Invalid JWT token", e);
        }
    }
}