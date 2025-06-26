package com.jangelcode.spring.app.service;

import com.jangelcode.spring.app.dto.CitaDTO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CitaService {
    List<CitaDTO> getCitas();

    List<CitaDTO> getCitasPorFecha(LocalDate fecha);

    CitaDTO reservarCita(CitaDTO citaDTO);

    List<CitaDTO> getCitasPorUsuario(String username);

    CitaDTO actualizarEstado(Long id, String nuevoEstado);

    Page<CitaDTO> getCitasPaginadas(LocalDate fecha, String estado, Pageable pageable);
}