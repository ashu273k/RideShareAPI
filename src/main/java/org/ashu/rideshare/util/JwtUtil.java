package org.ashu.rideshare.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    /**
     * Generate JWT token with username and role
     */

    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("role", role);

        return createToken(claims, username);
    }

    /**
     * Create token with custom claims
     */
    private String createToken(Map<String, Object> claims, String subject) {
        long nowMillis = System.currentTimeMillis();
        long expireMillis = nowMillis + jwtExpirationMs;

        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(expireMillis))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Extract username from token
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Extract role from token
     */
    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }

    /**
     * Extract all claims from token
     */
    private Claims extractClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Check if token is expired
     */
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    /**
     * Validate token (check signature and expiration)
     */
    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

}
