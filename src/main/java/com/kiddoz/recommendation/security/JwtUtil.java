package com.kiddoz.recommendation.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.stream.Collectors;


@Component
public class JwtUtil {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    private static final Long EXPIRATION_IN_SECONDS = 36000L;

    public JwtUtil(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Instant now = Instant.now();

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plusSeconds(EXPIRATION_IN_SECONDS))
                .subject(user.getUsername())

                .claim("roles", scope)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String getUsernameFromJwtToken(String token) {
        return jwtDecoder.decode(token).getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}

