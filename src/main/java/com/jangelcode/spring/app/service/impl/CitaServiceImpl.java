package com.jangelcode.spring.app.service.impl;

import com.jangelcode.spring.app.dto.CitaDTO;
import com.jangelcode.spring.app.entity.Cita;
import com.jangelcode.spring.app.entity.Rol;
import com.jangelcode.spring.app.entity.Usuario;
import com.jangelcode.spring.app.mapper.CitaMapper;
import com.jangelcode.spring.app.repository.CitaRepository;
import com.jangelcode.spring.app.repository.UsuarioRepository;
import com.jangelcode.spring.app.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
