package com.jangelcode.spring.app.service;

import com.jangelcode.spring.app.dto.CitaDTO;
import java.time.LocalDate;
import java.util.List;

public interface CitaService {
    List<CitaDTO> getCitas();

    List<CitaDTO> getCitasPorFecha(LocalDate fecha);

    CitaDTO reservarCita(CitaDTO citaDTO);

    List<CitaDTO> getCitasPorUsuario(String username);

}