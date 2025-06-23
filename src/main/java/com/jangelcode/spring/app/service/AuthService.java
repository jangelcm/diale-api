package com.jangelcode.spring.app.service;

import com.jangelcode.spring.app.dto.LoginRequest;
import com.jangelcode.spring.app.dto.RegisterRequest;
import com.jangelcode.spring.app.dto.TokenResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> register(RegisterRequest request);

    ResponseEntity<TokenResponse> login(LoginRequest request);

    ResponseEntity<TokenResponse> refresh(String authHeader);
}
