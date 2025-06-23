package com.jangelcode.spring.app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jangelcode.spring.app.exception.RestApiException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {
        Throwable cause = (Throwable) request.getAttribute("jwt_exception");
        RestApiException apiException;
        if (cause instanceof ExpiredJwtException) {
            apiException = new RestApiException(
                    "Token expirado",
                    "Tu sesión ha expirado. Por favor, vuelve a iniciar sesión.",
                    HttpStatus.UNAUTHORIZED.value());
        } else {
            apiException = new RestApiException(
                    "No autorizado",
                    "No tienes permisos para acceder a este recurso.",
                    HttpStatus.UNAUTHORIZED.value());
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), apiException);
    }
}