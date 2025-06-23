package com.jangelcode.spring.app.config;

import com.jangelcode.spring.app.security.JwtAuthenticationFilter;
import com.jangelcode.spring.app.security.CustomUserDetailsService;
import com.jangelcode.spring.app.security.JwtAuthEntryPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1) CSRF deshabilitado (para APIs con JWT)
        http.csrf(csrf -> csrf.disable())
                // 2) Habilitar sesiones stateless (JWT)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 3) Permitir iframes del mismo origen (necesario para H2-console)
                .headers(headers -> headers.frameOptions(frameOpts -> frameOpts.sameOrigin()))
                // 4) Configurar accesos públicos y protegidos
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/api/auth/**", "/public/**", "/api/citas/**",
                                "/h2-console/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**", "/v3/api-docs/**",
                                "/api-docs/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(eh -> eh.authenticationEntryPoint(jwtAuthEntryPoint))
                // 5) Registrar el UserDetailsService y el filtro JWT
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
