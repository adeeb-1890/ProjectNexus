package com.Adeeb.ProjectManagementSystem.SecurityConfiguration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Configuration
public class JwtProvider {

    private static final String SECRET_KEY =
            "lkdhfalhfio54644haofajnfs545454ahahkfahkfhihaknfkaiufggo65445we4w4wqw5e5rrq5we5we5w5wew5";

    private static final SecretKey key =
            Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(Authentication auth) {

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .claim("email", auth.getName())
                .signWith(key)
                .compact();
    }


    public static String getEmailFromToken(String jwt){
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();

        return String.valueOf(claims.get("email"));
    }

}
