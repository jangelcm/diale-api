package com.jangelcode.spring.app.mapper;

import com.jangelcode.spring.app.entity.Cita;
import com.jangelcode.spring.app.dto.CitaDTO;

public class CitaMapper {
    public static CitaDTO toDTO(Cita entity) {
        CitaDTO dto = new CitaDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setEmail(entity.getEmail());
        dto.setFecha(entity.getFecha());
        dto.setHora(entity.getHora());
        dto.setUsername(entity.getUsuario() != null ? entity.getUsuario().getUsername() : null);
        return dto;
    }

    public static Cita toEntity(CitaDTO dto) {
        Cita entity = new Cita();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setEmail(dto.getEmail());
        entity.setFecha(dto.getFecha());
        entity.setHora(dto.getHora());
        // El usuario se asigna en el service
        return entity;
    }
}
