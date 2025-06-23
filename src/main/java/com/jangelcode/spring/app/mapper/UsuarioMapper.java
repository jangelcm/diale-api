package com.jangelcode.spring.app.mapper;

import com.jangelcode.spring.app.dto.UsuarioDTO;
import com.jangelcode.spring.app.entity.Usuario;

public class UsuarioMapper {
    public static UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setRol(usuario.getRol());
        dto.setEmail(usuario.getEmail());
        return dto;
    }
}
