package com.prodapt.mobileplan.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.prodapt.mobileplan.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String SECRET =
            "THIS_IS_A_DEMO_SECRET_KEY_FOR_JWT_123456";

    private static final long EXPIRATION =
            1000 * 60 * 60; // 1 hour

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    @Override
    public String generateToken(String mobile) {

        return Jwts.builder()
                .setSubject(mobile)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    @Override
    public String extractMobile(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
