package com.jangelcode.spring.app.service.impl;

import com.jangelcode.spring.app.dto.LoginRequest;
import com.jangelcode.spring.app.dto.RegisterRequest;
import com.jangelcode.spring.app.dto.TokenResponse;
import com.jangelcode.spring.app.entity.Rol;
import com.jangelcode.spring.app.entity.Usuario;
import com.jangelcode.spring.app.exception.ResourceNotFoundException;
import com.jangelcode.spring.app.exception.UsuarioYaExisteException;
import com.jangelcode.spring.app.repository.UsuarioRepository;
import com.jangelcode.spring.app.security.JwtUtil;
import com.jangelcode.spring.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new UsuarioYaExisteException("El usuario ya existe");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Rol.USER);
        usuario.setEmail(request.getEmail());
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(Map.of("message", "Usuario registrado correctamente"));
    }

    @Override
    public ResponseEntity<TokenResponse> login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol().name());
            long expiresIn = jwtUtil.getExpirationMs() / 1000;
            String refreshToken = jwtUtil.generateRefreshToken(usuario.getUsername());
            return ResponseEntity.ok(new TokenResponse(token, expiresIn, refreshToken));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Credenciales inválidas");
        }
    }

    @Override
    public ResponseEntity<TokenResponse> refresh(String authHeader) {
        String refreshToken = authHeader.replace("Bearer ", "");
        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            return ResponseEntity.badRequest().build();
        }
        String username = jwtUtil.getUsernameFromRefreshToken(refreshToken);
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        String newToken = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol().name());
        long expiresIn = jwtUtil.getExpirationMs() / 1000;
        String newRefreshToken = jwtUtil.generateRefreshToken(usuario.getUsername());
        return ResponseEntity.ok(new TokenResponse(newToken, expiresIn, newRefreshToken));
    }
}
