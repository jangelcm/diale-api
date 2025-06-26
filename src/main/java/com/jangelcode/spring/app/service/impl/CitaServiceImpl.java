package com.jangelcode.spring.app.service.impl;

import com.jangelcode.spring.app.dto.CitaDTO;
import com.jangelcode.spring.app.entity.Cita;
import com.jangelcode.spring.app.entity.EstadoCita;
import com.jangelcode.spring.app.entity.Rol;
import com.jangelcode.spring.app.entity.Usuario;
import com.jangelcode.spring.app.mapper.CitaMapper;
import com.jangelcode.spring.app.repository.CitaRepository;
import com.jangelcode.spring.app.repository.UsuarioRepository;
import com.jangelcode.spring.app.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitaServiceImpl implements CitaService {
    @Autowired
    private CitaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<CitaDTO> getCitas() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        boolean isAdmin = usuario != null && usuario.getRol().equals(Rol.ADMIN);
        if (isAdmin) {
            return repository.findAll().stream().map(CitaMapper::toDTO).collect(Collectors.toList());
        } else {
            return repository.findAll().stream()
                    .filter(c -> c.getUsuario() != null && c.getUsuario().getUsername().equals(username))
                    .map(CitaMapper::toDTO).collect(Collectors.toList());
        }
    }

    @Override
    public List<CitaDTO> getCitasPorFecha(LocalDate fecha) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        boolean isAdmin = usuario != null && usuario.getRol().equals(Rol.ADMIN);
        if (isAdmin) {
            return repository.findByFecha(fecha).stream().map(CitaMapper::toDTO).collect(Collectors.toList());
        } else {
            return repository.findByFecha(fecha).stream()
                    .filter(c -> c.getUsuario() != null && c.getUsuario().getUsername().equals(username))
                    .map(CitaMapper::toDTO).collect(Collectors.toList());
        }
    }

    @Override
    public CitaDTO reservarCita(CitaDTO citaDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        Cita entity = CitaMapper.toEntity(citaDTO);

        if (usuario.isPresent()) {
            entity.setUsuario(usuario.get());
        }
        entity.setId(null);

        return CitaMapper.toDTO(repository.save(entity));
    }

    @Override
    public List<CitaDTO> getCitasPorUsuario(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (usuario == null)
            return List.of();
        return repository.findAll().stream()
                .filter(c -> c.getUsuario() != null && c.getUsuario().getUsername().equals(username))
                .map(CitaMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CitaDTO actualizarEstado(Long id, String nuevoEstado) {
        Cita cita = repository.findById(id).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        if (nuevoEstado != null) {
            try {
                cita.setEstado(EstadoCita.valueOf(nuevoEstado.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Estado no válido: " + nuevoEstado);
            }
        }
        return CitaMapper.toDTO(repository.save(cita));
    }

    @Override
    public Page<CitaDTO> getCitasPaginadas(LocalDate fecha, String estado, Pageable pageable) {
        Page<Cita> page;
        if (fecha != null && estado != null && !estado.isEmpty()) {
            try {
                page = repository.findByFechaGreaterThanEqualAndEstado(fecha, EstadoCita.valueOf(estado.toUpperCase()),
                        pageable);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Estado no válido: " + estado);
            }
        } else if (fecha != null) {
            page = repository.findByFechaGreaterThanEqual(fecha, pageable);
        } else if (estado != null && !estado.isEmpty()) {
            try {
                page = repository.findByEstado(EstadoCita.valueOf(estado.toUpperCase()), pageable);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Estado no válido: " + estado);
            }
        } else {
            page = repository.findAll(pageable);
        }
        return page.map(CitaMapper::toDTO);
    }
}
