package com.revature.utils;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.revature.exceptions.AuthenticationException;
import com.revature.models.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTManager {

    private final Key key;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public JWTManager() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String issueToken(User user) {
        // This builds the payload which is encrypted...
        // ...information about the user we're authenticating

        return Jwts.builder()
                .setId(String.valueOf(user.getId()))
                .setSubject(user.getEmail())
                .setIssuer("Full-Stack-Black-Jack API")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(key)
                .compact();
    }

    public int parseUserIDFromToken(String token) {
        // This method is used to parse a token from the request

        try {
            return Integer.parseInt(Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getId());

        } catch (Exception e) {
            log.warn("JWT error parsing user id from token");
            throw new AuthenticationException("Unable to parse user id from JWT. Please sign in again!");

        }
    }

}
