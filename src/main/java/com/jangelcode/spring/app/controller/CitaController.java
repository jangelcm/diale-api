package com.jangelcode.spring.app.controller;

import com.jangelcode.spring.app.dto.CitaDTO;
import com.jangelcode.spring.app.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    @Autowired
    private CitaService service;

    @GetMapping
    public List<CitaDTO> getCitas(
            @RequestParam(value = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        if (fecha != null) {
            return service.getCitasPorFecha(fecha);
        }
        return service.getCitas();
    }

    @PostMapping
    public ResponseEntity<CitaDTO> reservarCita(@RequestBody CitaDTO citaDTO) {
        return ResponseEntity.ok(service.reservarCita(citaDTO));
    }

    @GetMapping("/usuario/{username}")
    public List<CitaDTO> getCitasPorUsuario(@PathVariable String username) {
        return service.getCitasPorUsuario(username);
    }

}
