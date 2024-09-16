package com.projects.spring.projectmanager.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

//creating new tokens;
public class JWTProvider {
    static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());


    public static  String generateToken(Authentication auth){

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+86400000))
                .claim("email",auth.getName())
                .signWith(key)
                .compact();
    }

    public static String getEmailFromToken(String jwt){
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();

        return String.valueOf(claims.get("email"));
    }
}
