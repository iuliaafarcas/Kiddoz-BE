package com.kiddoz.recommendation.controller;

import com.kiddoz.recommendation.dto.LoginDto;
import com.kiddoz.recommendation.model.ApplicationUser;
import com.kiddoz.recommendation.security.JwtUtil;
import com.kiddoz.recommendation.service.ApplicationUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final ApplicationUserDetailsService applicationUserDetailsService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(), loginDto.getPassword()
                    )
            );

            User user = (User) authenticate.getPrincipal();

            return ResponseEntity.ok()
                    .body(
                            "Bearer " + jwtUtils.generateJwtToken(user.getUsername())
                    );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("userData")
    public ApplicationUser getUserData(Authentication authentication) {
        return applicationUserDetailsService.getApplicationUserByUsername(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<?> getUsernameFromToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(jwtUtils.getUsernameFromToken(token.substring(7)));
    }
}
