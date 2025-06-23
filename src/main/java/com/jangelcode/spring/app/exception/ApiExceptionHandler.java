package com.jangelcode.spring.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestApiException> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String[]> errores = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errores.add(new String[] { fieldName, errorMessage });
        });
        RestApiException apiException = new RestApiException(
                "Error de validación",
                "Uno o más campos tienen errores de validación.",
                errores,
                HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(apiException);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RestApiException> handleProductoMagistralNotFoundException(
            ResourceNotFoundException ex) {
        RestApiException apiException = new RestApiException(
                "Producto no encontrado",
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiException);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RestApiException> handleBadCredentials(BadCredentialsException ex) {
        RestApiException apiException = new RestApiException(
                "Credenciales inválidas",
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiException);
    }

    @ExceptionHandler(UsuarioYaExisteException.class)
    public ResponseEntity<RestApiException> handleUsuarioYaExiste(UsuarioYaExisteException ex) {
        RestApiException apiException = new RestApiException(
                "Usuario ya existe",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiException);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<RestApiException> handleUsuarioYaExiste(NullPointerException ex) {
        RestApiException apiException = new RestApiException(
                "Error por nulo",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiException);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<RestApiException> handleExpiredJwt(ExpiredJwtException ex) {
        RestApiException apiException = new RestApiException(
                "Token expirado",
                "Tu sesión ha expirado. Por favor, vuelve a iniciar sesión.",
                HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiException);
    }

}
