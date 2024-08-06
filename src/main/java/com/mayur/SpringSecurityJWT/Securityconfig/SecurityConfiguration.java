package com.mayur.SpringSecurityJWT.Securityconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static com.mayur.SpringSecurityJWT.Student.Repository.Entity.Role.ADMIN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static com.mayur.SpringSecurityJWT.Student.Repository.Entity.Role.STUDENT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

    String roles[] = {ADMIN.name(), STUDENT.name()};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req
                        -> req.requestMatchers("/api/Register")
                        .permitAll()
                        .requestMatchers("/api/login", "api/AllSubjects").permitAll()
                        .requestMatchers("/h2-console").permitAll()
                        .requestMatchers("auth/create", "auth/students/{id}", "auth/students").hasAnyRole(ADMIN.name())
                        .requestMatchers("auth/subjects").hasAnyRole(roles)
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
