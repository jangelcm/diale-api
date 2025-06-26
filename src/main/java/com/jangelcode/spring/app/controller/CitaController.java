package com.jangelcode.spring.app.controller;

import com.jangelcode.spring.app.dto.CitaDTO;
import com.jangelcode.spring.app.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    @PatchMapping("/{id}/estado")
    public ResponseEntity<CitaDTO> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        CitaDTO citaActualizada = service.actualizarEstado(id, nuevoEstado);
        return ResponseEntity.ok(citaActualizada);
    }

    @GetMapping("/paginado")
    public Page<CitaDTO> getCitasPaginadas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(value = "estado", required = false) String estado) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getCitasPaginadas(fecha, estado, pageable);
    }

}
