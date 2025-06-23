package com.jangelcode.spring.app.dto;

import com.jangelcode.spring.app.entity.Rol;

import jakarta.validation.constraints.NotBlank;

public class UsuarioDTO {
    private Long id;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;

    private Rol rol;

    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
