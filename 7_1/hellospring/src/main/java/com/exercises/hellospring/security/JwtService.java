package com.exercises.hellospring.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    // This service is used in AuthController and JwtAuthenticationFilter for whenever we need to work with JWT tokens
    // e.g: extracting user information, confirming token isn't expired, generating token for login, etc...
    
    @Value("${app.jwt.secret}")
    private String privateKey;

    public String generateToken(UserDetails userDetails) {
        // JSON Web Token
        String token = Jwts.builder().subject(userDetails.getUsername())
                                    .issuedAt(new Date())
                                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                                    .signWith(getSecretKey())
                                    .compact();
        return token;
    }

    public String extractUserName(String token) {
        // .build() gives us a JwtParser using our secret key to parse any tokens
        // .parseSignedClaims returns a Jws<Claims>, JWS = JSON Web Signature, a wrapper object that represents a parsed verified 
        // (against our secret key to ensure no tampering) JWT
        // Claims is essentially a Map<String, Object>, in our case it would look like this but we can add more fields
        /*
            {
                "sub": "user",
                "iat": 1741000000,
                "exp": 1741086400
            }
        */
        String userName = Jwts.parser().verifyWith(getSecretKey()).build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getSubject();
        return userName;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String userName = extractUserName(token);
        return userDetails.getUsername().equals(userName) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload().getExpiration();
        Date now = new Date();
        return now.after(expiration);
    }

    private SecretKey getSecretKey() {
        byte[] bytes = Decoders.BASE64.decode(privateKey);
        return Keys.hmacShaKeyFor(bytes);
    }
}
