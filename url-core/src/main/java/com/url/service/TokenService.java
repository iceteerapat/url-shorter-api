package com.url.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${com.url.jwt.header-name:Authorization}")
    private String headerName;
    @Value("${com.url.jwt.header-prefix:Bearer }")
    private String headerPrefix;
    @Value("${com.url.jwt.key}")
    private String tokenKey;
    @Value("${com.url.jwt.access-timeout}")
    private int accessTokenTimeout;


    public String generateToken(String customerNo) {
        long exp = System.currentTimeMillis() + System.currentTimeMillis() + (accessTokenTimeout * 60L * 1000L);
        Date expiryDate = new Date(exp);

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .header().add("typ", "JWT").and()
                .subject(customerNo)
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(tokenKey)), Jwts.SIG.HS512)
                .compact();
    }

    public Claims verifyToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(tokenKey)))
                .build()
                .parseSignedClaims(token.replace(headerPrefix, ""))
                .getPayload();
    }

    public boolean hasToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(headerName);
        if (authenticationHeader == null) {
            return false;
        } else if (!authenticationHeader.startsWith(headerPrefix)) {
            logger.info("invalid prefix [{}]", authenticationHeader);
            return false;
        }
        return true;
    }
}
