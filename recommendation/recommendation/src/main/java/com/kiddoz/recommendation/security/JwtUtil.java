package com.kiddoz.recommendation.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;


@Component
public class JwtUtil {
    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private Integer validity = 60 * 60 * 60 * 24 * 7;

    public String generateJwtToken(String user) {
        return Jwts.builder().setSubject(user)
                .signWith(key)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity * 1000))
                .compact();
    }

    public Boolean validateJwtToken(String token, String username) {
        return Objects.equals(getUsernameFromToken(token), username) && !tokenExpired(token);
    }

    public String getUsernameFromToken(String token) {
        var claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Boolean tokenExpired(String token) {
        var claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }
}

