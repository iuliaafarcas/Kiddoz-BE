package com.kiddoz.recommendation.configuration;

import com.kiddoz.recommendation.security.JwtAuthenticationEntryPoint;
import com.kiddoz.recommendation.security.JwtTokenFilter;
import com.kiddoz.recommendation.service.ApplicationUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtTokenFilter jwtTokenFilter;
    private final JwtAuthenticationEntryPoint jwtEntryPoint;
    private final ApplicationUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors().disable().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint).and()
                .authorizeHttpRequests().anyRequest().permitAll().and()
//                .authorizeHttpRequests(
//                        authorize -> authorize
//                                // Are acces oricine
//                                .requestMatchers("/**").permitAll()
//                        //.requestMatchers("/**").permitAll()
//                        // Cu rol
//                        //.requestMatchers("/rating").hasRole(Roles.USER_PARENT)
//                        // Cu JWT
//                        //.anyRequest().authenticated()
//                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder encoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(
                        username -> userDetailsService.loadUserByUsername(username)
                )
                .passwordEncoder(encoder)
                .and().build();
    }
}
