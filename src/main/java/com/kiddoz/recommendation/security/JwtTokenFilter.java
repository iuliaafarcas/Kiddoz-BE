package com.kiddoz.recommendation.security;

import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import com.kiddoz.recommendation.service.ApplicationUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    private final ApplicationUserRepository applicationUserRepository;
    private final ApplicationUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        logger.debug("Authorization header: {}", header);

        if (header == null || !header.startsWith("Bearer ")) {
            logger.debug("No JWT token found in request headers");
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.split(" ")[1].trim();
        logger.debug("JWT token extracted: {}", token);

        String userName = null;
        try {
            userName = jwtUtil.getUsernameFromJwtToken(token);
            logger.debug("Username extracted from JWT: {}", userName);
        } catch (Exception e) {
            logger.error("Error extracting username from JWT", e);
            filterChain.doFilter(request, response);
            return;
        }

        if (applicationUserRepository.findApplicationUserByEmail(userName) == null) {
            logger.warn("No user found with email from JWT: {}", userName);
            throw new AuthorizationServiceException(
                    "Invalid jwt email"
            );
        }

        if (!jwtUtil.validateJwtToken(token)) {
            throw new AuthorizationServiceException("Invalid jwt");
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.debug("Setting Authentication to SecurityContext for user: {}", userName);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails == null ? List.of() : userDetails.getAuthorities()
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
